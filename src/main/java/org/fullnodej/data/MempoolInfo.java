package org.fullnodej.data;

import lombok.Data;

/**@see <a href="https://bitcoin.org/en/developer-reference#getmempoolinfo">getmempoolinfo wiki</a>*/
@Data
public class MempoolInfo {

	/** count of transactions in mempool */
	int size;
	/** size of mempool in bytes */
	int bytes;

}
