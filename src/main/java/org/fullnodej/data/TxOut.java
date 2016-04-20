package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TxOut {

	/** header hash of local best block including this tx in hex. not mined=empty*/
	String bestblock;
	int confirmations;
	BigDecimal value;
	ScriptPubKey scriptPubKey;
	int version;
	boolean coinbase;

}
