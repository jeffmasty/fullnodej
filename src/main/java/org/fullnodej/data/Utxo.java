package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=true)
public class Utxo extends Outpoint {



	/**base58 P2PKH or P2SH address the output paid. Only returned for P2PKH or P2SH output scripts*/
	String address;

	/**If the address returned belongs to an account, this is the account. Otherwise not returned*/
	String account;

	/**hex of the output script paid*/
	String scriptPubKey;

	/**If the output is a P2SH whose script belongs to this wallet, this is the redeem script in hex*/
	String redeemScript;

	/**amount paid to the output in bitcoins*/
	BigDecimal amount;

	/**number of confirmations received for the transaction containing this output*/
	int confirmations;

	/** Added in Bitcoin Core 0.10.0, false watch-only outputs */
	boolean spendable;

}
