package org.fullnodej.data;

import java.math.BigDecimal;

import org.fullnodej.Rpc;

import lombok.Data;

/**Mining-related information
 * @see <a href="https://bitcoin.org/en/developer-reference#getmininginfo">getmininginfo wiki</a><br/> {@link Rpc#getmininginfo()}*/
@Data
public class MiningInfo {

	int blocks;
	int currentblocksize;
	int currentblocktx;
	BigDecimal difficulty;
	/** empty string if no warnings, sample errors String:
	 * <pre>WARNING: abnormally high number of blocks generated, 123 blocks received in the last 4 hours (24 expected)</pre>*/
	String errors;
	/**The limit on the number of processors for generation. If generation was enabled since the last time this rpc was restarted,
	 * this is the number used in the second parameter of the setgenerate. not mining = -1*/
	int genproclimit;
	/**Estimated hashes/second the network is generating. @see {@link Rpc#gethashespersec()}*/
	long networkhashps;
	/**number of transactions in the memory pool*/
	int pooledtx;
	/**false if this rpc is on mainnet or a regtest*/
	boolean testnet;
	/**main|test|regtest*/
	String chain;
	/**true if currently mining*/
	boolean generate;

}
/*Sample:    {blocks=764409, currentblocksize=0, currentblocktx=0, difficulty=1.0,
 * 	  errors=WARNING: abnormally high number of blocks generated, 123 blocks received in the last 4 hours (24 expected),
 *    genproclimit=-1, networkhashps=20617321657386, pooledtx=0, testnet=true, chain=test, generate=false}*/
