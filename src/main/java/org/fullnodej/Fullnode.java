package org.fullnodej;

import java.io.IOException;
import java.net.ConnectException;

import org.fullnodej.exception.ConfigurationException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

/**a URL identifies a Fullnode, user supplied rpcParams grant access to it, */
public class Fullnode {

	/**low level communications proxy with bitcoind node*/
	public final Rpc rpc;
	private final Util util;
	private final Wallet wallet;
	private final Notifications notifications;

	/**Create a basic (and test) connection to a running bitcoind node.
	 * @param params URL and password to connect to a running Bitcoind RPC interface
	 * @throws IOException if data was not retrievable from the bitcoind node as described by params*/
	public Fullnode(RpcParams params) throws IOException {
		rpc = createRpc(params);
		ping();
		util = new Util(rpc);
		notifications = new NotificationsImpl(rpc);
		wallet = new WalletImpl(rpc);
	}

	/**Creates a connection to Bitcoind, tests that connection for retrievable data,
	 * sets notificationsPort but does not start listeners */
	public Fullnode(RpcParams params, int notificationsPort) throws IOException {
		rpc = createRpc(params);
		ping();
		util = new Util(rpc);
		wallet = new WalletImpl(rpc);
		notifications = new NotificationsImpl(rpc, notificationsPort);
	}

	/**Creates a new Rpc to bitcoind, tests that connection, starts listening for notifications
	 * from bitcoind on the supplied port, and attaches the supplied listener.
	 * @param params
	 * @param notificationsPort
	 * @param notify
	 * @throws IOException if the Rpc could not be contacted, if listenerPort is blocked.
	 * @throws ConfigurationException invalid notificationPort */
	public Fullnode(RpcParams params, int notificationsPort, Notify notify) throws IOException, ConfigurationException {
		this(params, notificationsPort);
		notifications.attach(notify);
	}

	/**@return a connection to a Bitcoind RPC interface based on the supplied Url and password.
	 * The returned Rpc has not been ping()ed for password/url validity.*/
	public static Rpc createRpc(RpcParams params) {
		JsonRpcHttpClient client = new JsonRpcHttpClient(params.getUrl(), params.basicHeader());
		client.getObjectMapper().enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		client.getObjectMapper().enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		return ProxyUtil.createClientProxy(Rpc.class.getClassLoader(), Rpc.class, client);
	}

	/**try to pull data from bitcoind and present any exceptions that occur*/
	public void ping() throws ConnectException {
		rpc.getblockcount();
	}

	/**@return low level communications proxy to bitcoind node*/
	public final Rpc getRpc() { return rpc; }
	/**@return rpc helper for general/non-wallet related activity */
	public Util getUtil() { return util; }
	/**@return rpc helper for wallet related activity*/
	public Wallet getWallet() { return wallet; }
	/**@return The listener system (requires listenerPort configured and configuration on remote bitcoind node)*/
	public Notifications getNotifications() { return notifications; }

}
