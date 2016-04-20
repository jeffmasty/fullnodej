package org.fullnodej.data;

public enum ScriptType {

	/**a P2PK script inside P2SH*/
	pubkey,
	/**a P2PKH script inside P2SH*/
	pubkeyhash,
	/**a multisig script inside P2SH*/
	multisig,
	/**unkown*/
	nonstandard;

}
