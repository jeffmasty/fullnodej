package org.fullnodej.data;

import java.util.List;

import lombok.Data;

@Data
public class Script {

	/**Redeem script in decoded form with non-data-pushing opcodes listed. May be empty*/
	String asm;

	ScriptType type;

	/**Number of signatures required; this is always 1 for P2PK or P2PKH within P2SH.
	 * May be greater than 1 for P2SH multisig. Will not be returned for nonstandard*/
	int reqSigs;

	List<ScriptAddress> addresses;


}
