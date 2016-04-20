package org.fullnodej.data;

import java.util.ArrayList;

import lombok.Data;

/**Result of a <a href="https://bitcoin.org/en/developer-reference#decoderawtransaction">
 * decoderawtransaction</a> call */
@Data
public class Tx {

	/**transaction’s TXID encoded as hex in RPC byte order*/
	protected String txid;
	/**transaction format version number*/
	protected int version;
	/**@see locktime rules https://bitcoin.org/en/developer-guide#locktime_parsing_rules */
	protected long locktime;
	/** Inputs being spent array, https://bitcoin.org/en/glossary/input */
	protected ArrayList<Vin> vin;
	/** Outputs receiving funds, https://bitcoin.org/en/glossary/output */
	protected ArrayList<Vout> vout;

}
