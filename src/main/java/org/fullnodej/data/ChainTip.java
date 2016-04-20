package org.fullnodej.data;

import org.fullnodej.Rpc;

import lombok.Data;

/**Information about the highest-height block (tip) of each local block chain.
 * @see Rpc#getchaintips()
 * */
@Data
public class ChainTip {

	/**height of the highest block on this chain*/
	int height;
	/**hash of highest block as hex*/
	String hash;
	/**Number of blocks on this chain but not on the main chain.
	 * For the best chain this will be 0, for others it will be >= 1*/
	int branchlen;
	ChainTipStatus status;

}
