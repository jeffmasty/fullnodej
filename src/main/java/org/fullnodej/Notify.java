package org.fullnodej;

import org.fullnodej.data.Block;
import org.fullnodej.data.WalletTx;

/**Receives notifications from bitcoind (if configured - correctly).
 * Pass an instance to Fullnode's {@link NotificationsImpl#attach(Notify)} method.<br/>
 * Currently, callbacks are made on new threads.<br/><br/>
 * When finished with a Notify instance, be sure to Notifications.detach() it,
 * or Notifications.shutdown() when finished with the notifications port. */
public interface Notify {

	/**A new block has been mined.
	 * @param block the block at the end of the longest chain */
	void blockNotify(Block block);

	/**A change that affects the wallet was detected.
	 * @param tx your guess is as good as mine*/
	void walletNotify(WalletTx tx);

	/**WARNING: THE BITCOIN NODE FELT THE NEED TO BROADCAST AN ALERT*/
	void alertNotify(String alert);

	/**Some process is taking notificationsPort offline*/
	void turnedOff();

}

