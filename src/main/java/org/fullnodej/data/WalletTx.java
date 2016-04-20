package org.fullnodej.data;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class WalletTx {

	/**positive if tx increased the wallet balance,
	 * negative if decreased, 0 if no effect */
	BigDecimal amount;
	/**fee paid (as negative value), if outgoing */
	BigDecimal fee;
	/**number of confirmations the transaction has received. Will be 0 for unconfirmed and -1 for conflicted*/
	int confirmations;
	/** true for mined txs, else null */
	Boolean generated;
	/** hex of best chain's containing block, only if confirmations > 0*/
	String blockhash;
	/** height of best chain's containing block, only if confirmations > 0*/
	int blockindex;
	/** header time of best chain's containing block, only if confiramtions > 0*/
	long blocktime;
	/**hex string of this here wallet transaction*/
	String txid;
	/**TXIDs of conflicting txs that spend the same inputs, possibly empty*/
	List<String> walletconflicts;

	/**when the tx was added to the wallet*/
	long time;
	/**when the zeroconf was detected, or time of containing block */
	long timereceived;

	String account;
	String otheraccount;
	String address;
	TxCategory category;
	int vout;

	/** locally stored comment field*/
    String comment;
    /** locally stored to comment field*/
    String to;

    /**one object for each input or output affecting the wallet, not returned in listsinceblock */
	List<WalletTxDetails> details;

	/**tx serialized in hex format, not returned in listsinceblock */
    String hex;

}
