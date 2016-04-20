package org.fullnodej.data;

import java.math.BigDecimal;
import java.util.List;

import org.fullnodej.Rpc;

import lombok.Data;

/**@see <a href="https://bitcoin.org/en/developer-reference#getblock">getblock wiki</a><br/>
 * {@link Rpc#getblock(String)}*/
@Data
public class Block {

	private List<String> tx;
	private long time;
	private int height;
	private long nonce;
	private String hash;
	private String bits;
	private BigDecimal difficulty;
	private String merkleroot;
	private String previousblockhash;
	private String nextblockhash;
	private int confirmations;
	private int version;
	private int size;
	private String chainwork;

}
