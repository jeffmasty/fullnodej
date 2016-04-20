package org.fullnodej.data;

import java.math.BigDecimal;
import java.util.ArrayList;

import lombok.Data;

/**The <a href="https://bitcoin.org/en/developer-reference#getblockchaininfo">getblockchaininfo RPC</a>
 * provides information about the current state of the block chain.*/
@Data
public class BlockchainInfo {

	/** main, test, or regtest */
	String chain;

	long blocks;

	/**number of validated headers in the local best headers chain.
	 * may be higher than the number of blocks*/
	long headers;

	String bestblockhash;

	/**The difficulty of the highest-height block in the best block chain*/
	BigDecimal difficulty;

	/**Estimate of what percentage of the block chain transactions have been verified
	 * so far, starting at 0.0 and increasing to 1.0 for fully verified. May slightly
	 * exceed 1.0 when fully synced to account for transactions in the memory pool*/
	BigDecimal verificationprogress;

	/**estimated number of block header hashes checked from the genesis block to
	 * this block, encoded as big-endian hex*/
	String chainwork;

	Boolean pruned;

	ArrayList<Softfork> softforks;

}
