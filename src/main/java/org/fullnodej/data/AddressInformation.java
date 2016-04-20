package org.fullnodej.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**@see <a href="https://bitcoin.org/en/developer-reference#validateaddress">validateaddress wiki</a>*/
@Setter @EqualsAndHashCode @ToString
public class AddressInformation {

	/** true for valid P2PKH or P2SH addresses*/
    boolean isvalid;
    /** The address as was requested, if it is valid*/
	@Getter String address;
	/** undocumented hex string */
	@Getter String scriptPubKey;
	/** true if the address is part of a wallet account */
    boolean ismine;
    /** true if the address is part of the wallet but is only being watched (no private key)*/
    boolean iswatchonly;
    /** true if the address is a P2SH address, null if not in wallet.*/
    boolean isscript;
    /**non-null for P2SH address requests belonging to this wallet, or null.<br/>
     * Type of script returned:
     * <ul><li><code>pubkey</code> for a P2PK script inside P2SH</li>
     * <li><code>pubkeyhash</code> for a P2PKH script inside P2SH</li>
     * <li><code>multisig</code> for a multisig script inside P2SH</li>
     * <li><code>nonstandard<code> for unknown scripts</li><ul>*/
    @Getter ScriptType script;

    /**redeem script encoded as hex, for P2SH address requests belonging to this wallet*/
    @Getter String hex;
    /**P2PKH addresses used in this script or computed P2PKH addresses of pubkeys,
     * for P2SH address requests belonging to the wallet, or null*/
    @Getter String[] addresses;
    /**number of signatures required by the script for P2SH addresses belonging to the wallet*/
    @Getter Integer sigrequired;
    /**hex string of public key for P2PKH AddressInfo requests belonging to the wallet*/
    @Getter String pubkey;
    /**compression setting of P2PKH AddressInfo requests belonging to the the wallet*/
    boolean iscompressed;
    /**Wallet Account the address belongs to, empty string for default account, null if not in wallet*/
    @Getter String account;

    public boolean isValid() {return isvalid;}
    public boolean isMine() {return ismine;}
    public boolean isWatchOnly() {return iswatchonly;}
    public boolean isScript() {return isscript;}
    public boolean isCompressed() {return iscompressed;}

}
