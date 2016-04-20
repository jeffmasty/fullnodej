package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Vout {

	/**The number of bitcoins paid to this output. May be 0*/
	protected BigDecimal value;
	/** output index */
	protected int n;
	protected ScriptPubKey scriptPubKey;

}
