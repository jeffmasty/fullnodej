package org.fullnodej;

import java.io.IOException;

import org.fullnodej.exception.ConfigurationException;

public interface Notifications {

	/**@return the currently configured listenerPort or null*/
	Integer getListenerPort();

	/**Configure the listener port to match the bitcoind configuration in order to receive notifications.
	 * @param port
	 * @throws ConfigurationException if listenerPort has previously been set and a Notify is currently attached to that port.
	 * @throws IOException if the port is not free or cannot be opened during the execution of this call*/
	void setListenerPort(int port) throws ConfigurationException, IOException;

	/**@return true if the listening port is engaged. The port is engaged when at least one Notify is attached*/
	boolean isListening();

	/**Connect a listener to the Notifications system
	 * @param notify the callback
	 * @throws IOException if listenerPort is not set or cannot connect
	 * @throws ConfigurationException if the listenerPort has not been configured */
	void attach(Notify notify) throws IOException, ConfigurationException;

	/**Disconnected a Notify target. When the last Notify has been detached, listenerPort will be freed.
	 * @param notify the listener to halt communications with
	 * @return true if the listener was previously in the list of callbacks*/
	boolean detach(Notify notify);

	/**Frees the port and halts communications with all Notify listeners*/
	void shutdown();

}