package org.fullnodej.data;

import lombok.Data;

/**P2SH address and hex-encoded redeem script*/
@Data
public class RedeemScript {

	/**P2SH address for this multisig redeem script*/
	String address;
	/**hex multisig redeem script*/
	String redeemScript;

}
