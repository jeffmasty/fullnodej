package org.fullnodej.data;

import lombok.Data;

@Data
public class Vin {

	protected String txid;
	/** output index of the outpoint being spent, null? for coinbase */
	protected Integer vout;
	/** describes the script of the input, null for coinbase */
	protected ScriptSig scriptSig;
	/** similar to scriptSig's hex field, null for standard/non-coinbase txs */
	protected String coinbase;
	/**input sequence number*/
	protected long sequence;

}
