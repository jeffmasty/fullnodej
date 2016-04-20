package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WalletTxDetails {

	/** true if watch-only, else null.<br/>added in 0.10.0*/
	Boolean involvesWatchonly;

	/**locally stored account credited or debited, can be the empty string */
	String account;
	String address;

	TxCategory category;
	/**A negative bitcoin amount if sending payment; a positive bitcoin amount if receiving payment (including coinbases)*/
	BigDecimal amount;
	/**For an output, the output index (vout) for this output in this transaction.
	 * For an input, the output index for the output being spent in its transaction.<br/>
	 * Added in Bitcoin Core 0.10.0*/
	int vout;
	/** If sending payment, the fee paid as a negative bitcoins value. May be 0. Not returned if receiving payment*/
	BigDecimal fee;



}
