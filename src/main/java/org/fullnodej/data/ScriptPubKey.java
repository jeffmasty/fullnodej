package org.fullnodej.data;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=true)
public class ScriptPubKey extends ScriptSig {

	public static enum Type {
		/**P2PK script*/ pubkey,
		/**P2PKH script*/ pubkeyhash,
		/**P2SH script*/ scripthash,
		/**bare multisig script*/ multisig,
		/**nulldata scripts*/ nulldata,
		/**unknown scripts*/ nonstandard
	}

	Type type;
	/**The number of signatures required; this is always 1 for P2PK, P2PKH,
	 * and P2SH (including P2SH multisig because the redeem script is not
	 * available in the pubkey script). It may be greater than 1 for bare multisig.
	 * This value will not be returned for nulldata or nonstandard script {@link #type}*/
	Integer reqSigs;
	/**P2PKH or P2SH addresses used in this transaction, or the computed P2PKH
	 * address of any pubkeys in this transaction. This array will not be
	 * returned for nulldata or nonstandard script types.
	 * boolean coinbase? */
	private ArrayList<String> addresses;

}
