package org.fullnodej;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import org.fullnodej.data.Block;
import org.fullnodej.data.WalletTx;
import org.fullnodej.exception.ConfigurationException;
import org.fullnodej.exception.ConfigurationException.Type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationsImpl implements Notifications {

	private final Rpc rpc;
	private final Set<Notify> callbacks = new HashSet<>();

	/** open port bitcoind sends data to. Necessary to configure bitcoind command line settings with this port, i.e., 7931:
	 * -blocknotify="echo '%s' | nc 127.0.0.1 7931" -walletnotify="echo '%s' | nc 127.0.0.1 7931" -alertnotify="echo '%s' | nc 127.0.0.1 7931"*/
	private Integer listenerPort;

	private ServerSocket server;
	private Socket socket;
	private SocketConnecter connector;
	private Thread wireHandler;

	public NotificationsImpl (Rpc rpc) {
		this.rpc = rpc;
	}

	public NotificationsImpl(Rpc rpc, int port) throws IOException {
		// openable(port);
		this.rpc = rpc;
		this.listenerPort = port;
	}

	public NotificationsImpl(Rpc rpc, Notify callback, int port) throws IOException, ConfigurationException {
		this(rpc, port);
		callbacks.add(callback);
		startup();
	}

	/* (non-Javadoc) @see org.fullnodej.Notifications#getListenerPort()*/
	@Override public Integer getListenerPort() {
		return listenerPort;
	}

	/* (non-Javadoc) @see org.fullnodej.Notifications#setListenerPort(int) */
	@Override public void setListenerPort(int port) throws ConfigurationException, IOException {
		if (isListening()) throw new ConfigurationException(Type.ALREADY_RUNNING, listenerPort);
		listenerPort = openable(port);
	}

	/* (non-Javadoc) @see org.fullnodej.Notifications#isListening() */
	@Override public boolean isListening() {
		return socket != null || connector != null;
	}

	/* (non-Javadoc) @see org.fullnodej.Notifications#attach(org.fullnodej.Notify) */
	@Override public void attach(Notify notify) throws IOException, ConfigurationException {
		if (notify == null) throw new ConfigurationException(Type.NULL_NOTIFY);
		if (callbacks.contains(notify)) throw new ConfigurationException(Type.DUPLICATE_NOTIFY);
		if (listenerPort == null || listenerPort.intValue() == 0) throw new ConfigurationException(Type.INVALID_LISTENER_PORT);
		callbacks.add(notify);
		if (!isListening()) startup();
		log.debug("attached " + id(notify) + " port: " + server.getLocalPort());
	}

	/* (non-Javadoc) @see org.fullnodej.Notifications#detach(org.fullnodej.Notify)*/
	@Override public boolean detach(Notify notify) {
		log.debug("detached " + id(notify));
		boolean result = callbacks.remove(notify);
		if (result && callbacks.size() == 0)
			shutdown();
		return result;
	}

	/* (non-Javadoc) @see org.fullnodej.Notifications#shutdown() */
	@Override public void shutdown() {
		log.debug("Turning Off Notifications...");
		if (wireHandler != null) wireHandler.interrupt();
		if (connector != null) connector.interrupt();
		for (Notify notify : callbacks)
			try {
				notify.turnedOff();
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
			}
		if (socket != null && !socket.isClosed())
			try {
				socket.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			} finally {
				socket = null;
			}
		if (server != null)
			try {
				server.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			} finally {
				server = null;
			}
	}

	private void startup() throws IOException, ConfigurationException {
		if (listenerPort == null) throw new ConfigurationException(Type.INVALID_LISTENER_PORT);
		if (isListening()) throw new ConfigurationException(Type.ALREADY_RUNNING);
		log.debug("Turning on notifications...");
		if (server == null) server = new ServerSocket(openable(listenerPort));
		new SocketConnecter().start();
		try {
			Thread.sleep(10); // allow startup time
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	///////////////////////////////////////////////////////////////////////////
	public static enum Msg {BLOCK, ALERT, WALLET}

	/**Opens and closes a port to prove that it ensure it can be opened.
	 * @param port port to check
	 * @return port, if it was able to be opened.
	 * @throws ConfigurationException if port is 0, invalid port number, or otherwise opening then closing port failed*/
	public static int openable(int port) throws IOException {
		if (port == 0) throw new IOException("port 0 not accepted.");
		new ServerSocket(port).close();
		return port;
	}

	private static String id(Object o) {
		return o.getClass().getSimpleName() + ":" + o.hashCode();
	}

	class SocketConnecter extends Thread {
		SocketConnecter() {
			super(SocketConnecter.class.getSimpleName());
			socket = null;
		}
		@Override public void run() {
			connector = this;
			try {
				if (server == null) server = new ServerSocket(listenerPort);
				log.trace("Notifications starting on port: " + server.getLocalPort());
				socket = server.accept();
				log.trace("notifications connected. Starting to receive data.");
				new WireHandler().start();
			} catch (IOException e) {
				// OK to be listening on accept() while an external service calls for shutdown
				log.info(e.getMessage());
			} finally {
				if (connector == this) { connector = null; }
			}
		}
	}

	/**Bitcoind has the ability to send different messages to different ports, however,
	 * this design is using 1 port for a simpler configuration. Because all messages
	 * types are sent to the same port, differentiate them here (and retrieve Object representation)*/
	class WireHandler extends Thread {

		/** possibly exciting, but now open a new connection and listen for possible new messages */
		private void respawn() throws IOException {
	    	socket.close();
	    	new SocketConnecter().start();
		}

		@Override public void run() {
			wireHandler = currentThread();
			if (socket == null) { log.error("no notifications"); return; }
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (!currentThread().isInterrupted()) {
				    String msg = reader.readLine();
				    if (msg == null)
				    	try {
				    		sleep(20l);
				    		continue;
				    	} catch (InterruptedException e) { }

				    msg = msg.trim();
					if (msg.startsWith("000") && msg.equals(rpc.getblockhash(rpc.getblockcount()))) {
						new BlockNotify(rpc.getblock(msg)).label().start();
						respawn();
						return;
					}

					try {
						if (rpc.getrawtransaction(msg) != null) {
							WalletTx tx = rpc.gettransaction(msg);
							new WalletNotify(tx).label().start();
							respawn();
							return;
						}
					} catch (Throwable t) { /* log.info(t.getMessage()); */ }

					try {
						Block block = rpc.getblock(msg);
						new BlockNotify(block).label().start();
						respawn();
						return;
					} catch (Throwable t) { }
						new AlertNotify(msg).label().start();
						respawn();
						return;
					}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	static abstract class Notifier extends Thread {
		static long msg_count;
		@Override abstract public void run();
		final Notifier label() {
			setName(this.getClass().getSimpleName().replace("Handler", "") + "-msg" + ++ msg_count);
			return this;
		}
	}

	class BlockNotify extends Notifier {
		final Block block;
		BlockNotify(Block block) { this.block = block; }
		@Override public void run() {
			for (Notify l : callbacks)
    			try {
    				l.blockNotify(block);
				} catch (Throwable t) {
					log.error(t.getMessage(), t);
				}
		}
	}

	class WalletNotify extends Notifier {
		final WalletTx tx;
		WalletNotify(WalletTx tx) { this.tx = tx; }
		@Override public void run() {
			for (Notify l : callbacks)
    			try {
    				l.walletNotify(tx);
				} catch (Throwable t) {
					log.error(t.getMessage(), t);
				}
		}
	}

	class AlertNotify extends Notifier {
		final String alert;
		AlertNotify(String alert) { this.alert = alert; }
		@Override public void run() {
			for (Notify l : callbacks)
    			try {
    				l.alertNotify(alert);
				} catch (Throwable t) {
					log.error(t.getMessage(), t);
				}
		}
	}

}
