package org.fullnodej.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=true)
public class Dependency extends Outpoint {

	/**output’s pubkey script encoded as hex*/
	String scriptPubKey;

	/**if the pubkey script was a script hash, this must be the corresponding redeem script*/
	String redeemScript;

}
