package org.fullnodej.data;

import org.fullnodej.Rpc;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=true)
public class VerboseTx extends Tx {

	/** Tx as hex string, same as returned by {@link Rpc#getrawtransaction(String)}*/
	String hex;
	/**hex string of the enclosing block header if this has been mined*/
	String blockhash;
	/** greater than 0 if this has been mined*/
	int confirmations;
	/** time of block if this has been mined */
	long time;
	/** #winning: "This field is currently identical to the time field described above" */
	long blocktime;

}
