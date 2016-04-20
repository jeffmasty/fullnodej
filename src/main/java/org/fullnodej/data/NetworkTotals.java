package org.fullnodej.data;

import lombok.Data;

/**@see <a href="https://bitcoin.org/en/developer-reference#getnettotals">getnettotals wiki</a>*/
@Data
public class NetworkTotals {

	/**total number of bytes received since the rpc was last restarted*/
	long totalbytesrecv;
	/**total number of bytes sent since the rpc was last restarted*/
	long totalbytessent;
	/** system time (not rpc adjusted time) */
	long timemillis;

}
