package org.fullnodej;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.fullnodej.data.*;

import com.googlecode.jsonrpc4j.JsonRpcClientException;

/**RPC version 0.11.2
 * <br/><br/>
 *
 * The proxy that is {@link Fullnode#createRpc(RpcParams) created } to direct RPC
 * communications with a Bitcoin fullnode implements this interface.
 * <br/><br/>
 *
 * If an object is given where a primitive would suffice, the nullability of that object should be considered part of the API.<br/>
 * Rather than have every method declare Auth/Socket/IO exceptions, Auth and IO errors are usually runtime
 * {@link JsonRpcClientException}. Exceptions are mentioned in the Javadoc if user input or otherwise may come occur with reasonable use.*/
public interface Rpc {

	/**Adds a P2SH multisig address to the wallet (a nrequired-to-sign multisignature address).
	 * @see <a href="https://bitcoin.org/en/developer-reference#addmultisigaddress">addmultisigaddress wiki</a>
	 * @param nrequired minimum required signatures
     * @param keys Each key. A public key against which signatures will be checked. Alternatively,
     * this may be a P2PKH address belonging to the wallet—the corresponding public key will be substituted.
     * There must be at least as many keys as specified by the Required parameter, and there may be more
	 * @return The P2SH multisig base58 address. The address will also be added to the wallet,
	 * and outputs paying that address will be tracked by the wallet
	 * @TODO test*/
	String addmultisigaddress(int nrequired, List<String> keys);

	/**If [account] is specified, assign address to [account].
	 * @param nrequired
	 * @param keys Each key is a bitcoin address or hex-encoded  key.
	 * @param account
	 * @return P2SH multisig base58 address. The address will also be added to the wallet,
	 * and outputs paying that address will be tracked by the wallet
	 * @TODO test*/
	String addmultisigaddress(int nrequired, List<String> keys, String account);

	/**@see <a href="https://bitcoin.org/en/developer-reference#addnode">addnode wiki</a>
	 * @see #getaddednodeinfo()
	 * @param peer ipAddress:port
	 * @param command add|remove|onetry
	 * @throws JsonRpcClientException for removing a node that is not on the addnode list*/
	void addnode(String nodeAddress, String command);

	/** copies wallet.dat to destination
	 * @param destination a directory or a path with filename */
	void backupwallet(String destination);

	/**Creates a P2SH multisignature address.
	 * @see <a href="https://bitcoin.org/en/developer-reference#createmultisig">createmultisig wiki</a>
	 * @param nrequired minimum signatures required to spend
	 * @param keys array of public keys or addresses. A public key against which signatures will be checked.
	 * If wallet support is enabled, this may be a P2PKH address belonging to the wallet—the corresponding
	 * public key will be substituted. There must be at least as many keys as specified by the
	 * Required parameter, and there may be more
	 * @return P2SH address and hex-encoded redeem script
	 * @TODO test*/
	RedeemScript createmultisig(int nrequired, List<String> keys);

	/**creates an unsigned serialized transaction that spends a previous output to a new output with a
	 * P2PKH or P2SH address. The transaction is not stored in the wallet or transmitted to the network.
	 * @param outpoints unspent outpoints
	 * @param outputs destinations and amounts
	 * @return unsigned raw transaction in hex
	 * @TODO test*/
	String createrawtransaction(List<Outpoint> outpoints, Map<String, BigDecimal> outputs);

	/**@see <a href="https://bitcoin.org/en/developer-reference#decoderawtransaction">decoderawtransaction wiki</a>
	 * @see #getrawtransaction(String, Integer)
	 * @param hex raw transaction in <a href="https://bitcoin.org/en/glossary/serialized-transaction">
	 * 	serialized transaction</a> format
	 * @return the hex string translated into a {@link Tx}*/
	Tx decoderawtransaction(String hex);

	 /**@see <a href="https://bitcoin.org/en/developer-reference#decodescript">decode script wiki</a>
	  * @param hex Redeem Script
	  * @return Script translated to human readable form (debatable)
	  * @TODO*/
	Script decodeScript(String hex);

	/**@see <a href="https://bitcoin.org/en/developer-reference#dumpprivkey">dumpprivkey wiki</a>
	 * @requires unlocked wallet
	 * @param address P2PKH address corresponding to the private key you want returned. Must contained in the wallet.
	 * @return The private key encoded as base58check using
	 * wallet import format <a href="https://bitcoin.org/en/developer-guide#wallet-import-format-wif">(WIF)</a>
	 * @throws JsonRpcClientException if address not in wallet, if wallet locked.*/
	String dumpprivkey(String address);

	/**Creates or overwrites a file with all wallet keys in a human-readable format:
	 * <pre>
	# Wallet dump created by Bitcoin v0.11.2.0-g7e27892 (Tue, 10 Nov 2015 14:46:18 +0100)
	# * Created on 2016-04-15T22:46:48Z
	# * Best block at time of backup was 766111 (0000000000dae7c777671d34aac6b740fffce8970215519a62f99931fba82128),
	#   mined on 2016-04-16T00:32:37Z

	cT7iL2VbaekzWhDUspMXErK8g1ZaQBWWB8QJ8egLYrdWthFcBHRD 1970-01-01T00:00:01Z label=fullnodej-1460750200770 # addr=miP6rFopSz5NZFwQmwkqabrkUhLUrPE6wJ
	cT62PbxucRUJuf2jXX4vh7FgxMB9GRzbVZkpRQCmo82nPp6Cx8NW 1970-01-01T00:00:01Z label=fullnodej-1460750902435 # addr=mu4PCFZbAtzywL3XricNVcA7XEhwLv67wi
	cRu3MqWsQr1NJGPKsq4ggTEd9xLRqFTGYEHVisRxCxghp6dwCVag 1970-01-01T00:00:01Z label=fullnodej-1460737514900 # addr=mz6CGr135HpEP6ptfTHZ3DqPZVVsxKuSdx
	cV762ikYhieisd3W6EQ3QYWN8KHDSobaSmRDx321s7UihPVW5HQE 2016-04-11T23:41:22Z change=1 # addr=mfbDiBRQHU4ttwps8aWdemWeCTS2kvigG1
	cQwsdE3dGaGGGJAPMBUpwCzvzeZmCZsVxCx7HTaWDFgfb1uQmgdc 2016-04-11T23:41:22Z change=1 # addr=mgFWRg8w8r5BqEZj9KeNpj3fUoJWEi4PUx
	...
	cVcRGz2ysztPtj3x8LLLiT8mq2sNg6RQRJQZwKhcbDPxWG9ctgCZ 2016-04-15T16:40:10Z reserve=1 # addr=n17Hr7hLnDsVRv1psYteiatekPaQUrg8sA

	# End of dump</pre>
	 * @see <a href="https://bitcoin.org/en/developer-reference#dumpwallet">dumpwallet wiki</a>
	 * @see #importwallet
	 * @requires unlocked wallet
	 * @param absolutePath
	 * @throws JsonRpcClientException locked wallet, bad file*/
    void dumpwallet(String absolutePath);

	/**Encrypts the wallet with a passphrase. This is only to enable encryption for the first time. After encryption is enabled,
	 * a passphrase is needed to use private keys.<br/>
	 * NOTE: the fullnode will shutdown and will need to be restarted after encryption occurs.
	 * @param passphrase
	 * @return "wallet encrypted; Bitcoin server stopping, restart to run with encrypted
	 * 					 wallet. The keypool has been flushed, you need to make a new backup."*/
	String encryptwallet(String passphrase);

	/**Estimates the transaction fee per kilobyte that needs to be paid for a transaction to be included within a certain number of blocks.
	 * @see <a href="https://bitcoin.org/en/developer-reference#estimatefee">estimatefee wiki</a>
	 * @param blocks
	 * @return The estimated fee the transaction should pay in order to be included within the specified number of blocks.
	 * If the rpc doesn’t have enough information to make an estimate, the value -1 will be returned. */
	BigDecimal estimatefee(int blocks);

	/**Estimates the transaction fee per kilobyte that needs to be paid for a transaction to be included within a certain number of blocks.
	 * @see <a href="https://bitcoin.org/en/developer-reference#estimatepriority">estimatepriority wiki</a>
	 * @see #estimatefee(int)
	 * @param blocks maximum number of blocks wanting to wait
	 * @return Estimated priority the transaction should have in order to be included within the specified number of blocks.
	 *     -1 returned if the rpc doesn’t have enough information to make an estimate.  During testing on testnet it has always returned -1*/
	BigDecimal estimatepriority(int blocks);

    /**Create blocks instantly, but only on regtest network.
     * @param blocks number of blocks
     * @return hashes of the blocks just mined
     * @TODO test*/
    List<String> generate(int blocks);

	/**@see <a href="https://bitcoin.org/en/developer-reference#getaccount">getaccount wiki</a>
	 * @param bitcoinAddress
	 * @return label associated with the given address (empty string for default account <b>or not in wallet</b>).<br/>
	 * @throws JsonRpcClientException if address is not a valid address (HTTP500)*/
	String getaccount(String address);

	/**@see <a href="https://bitcoin.org/en/developer-reference#getaccountaddress">getaccountaddress wiki</a>
	 * @see #getaddressesbyaccount(String)
	 * @param account
	 * @return the current Bitcoin address for receiving payments to this account.
	 * If the account doesn’t exist, both the account and a new address for receiving payment are created.
	 * Once a payment has been received to an address, future calls for the same account will return a different address.*/
    String getaccountaddress(String account);

	/**@see <a href="https://bitcoin.org/en/developer-reference#getaddednodeinfo">getaddednodeinfo wiki</a>
	 * @param details true for more info
	 * @param peer ipAddress:port if provided, otherwise all added node info will be returned
	 * @return list of added node info */
	List<AddedNodeInfo> getaddednodeinfo(boolean details, String nodeAddress);
	List<AddedNodeInfo> getaddednodeinfo(boolean details);

	/**@see <a href="https://bitcoin.org/en/developer-reference#getaddressesbyaccount">getaddressesbyaccount wiki</a>
	 * @param account account label
	 * @return all addresses associated with the account.  Empty array if the account doesn't exist (has no associated addresses).*/
	List<String> getaddressesbyaccount(String account);

	/**@return the server's total available balance. */
	BigDecimal getbalance();

	/**@return the balance in the account for all transactions having at least 1 confirmation*/
	BigDecimal getbalance(String account);

	/**@return the balance of the account counting only transactions that have been mined minConf blocks in the past*/
	BigDecimal getbalance(String account, int minConf);

	BigDecimal getbalance(String account, int minConf, boolean includeWatchOnly);

	/**@return header hash of the most recent block on the best block chain*/
	String getbestblockhash();

	/**@param blockHash
	 * @return a block for header hash from the local block database as a JSON object*/
	Block getblock(String blockHash);
	/**@return block for header hash, in hex format if asJson is false*/
	Object getblock(String hash, boolean asJson);

	/**@return information about the current state of the block chain.<br/>Added in Bitcoin Core 0.9.2*/
	BlockchainInfo getblockchaininfo();

	/**@return the number of blocks in the longest block chain*/
	int getblockcount();

	/**@return hash of block in best-block-chain at height; index 0 is the genesis block*/
	String getblockhash(long blockHeight);

	/**--beyond scope at the moment--*/
	Object getblocktemplate(Object request);

	/**@see <a href="https://bitcoin.org/en/developer-reference#getchaintips">getchaintips wiki</a>
	 * @return info about the highest-height block of each lock block chain*/
    List<ChainTip> getchaintips();

	/**@return the number of connections to other nodes.*/
	int getconnectioncount();

	/**@return the proof-of-work difficulty as a multiple of the minimum difficulty.*/
	BigDecimal getdifficulty();

	/**@return true if bitcoind is currently mining hashes */
	boolean getgenerate();

	/**@return {@link Info} containing various state info of the bitcoin server.<br/>
	 * Deprecated: will be removed in a later version of Bitcoin Core. Use
	 * GetBlockChainInfo, GetMemPoolInfo, GetMiningInfo, GetNetworkInfo, GetWalletInfo*/
	Info getinfo();

	/**@see <a href="https://bitcoin.org/en/developer-reference#getmempoolinfo">getmempoolinfo wiki<a>
	 * @return {@link MempoolInfo}: tx count and bytes*/
	MempoolInfo getmempoolinfo();

	/**@see <a href="https://bitcoin.org/en/developer-reference#getmininginfo">getmininginfo wiki</a>
	 * @return {@link MiningInfo}: various mining-related information*/
	MiningInfo getmininginfo();

	/**@return {@link NetworkTotals} since the rpc was restarted */
	NetworkTotals getnettotals();

	/**estimated current or historical network hashes/second.
	 * @param nblocks number of blocks to average together for estimated hashers/second
	 * @param height last block to use for calculating the average.
	 * default = -1 = tip of best chain, a height in the future is set to -1;
	 * @return estimated hashes/sec for params, maybe 0 for geneisis block,
	 * maybe negative if tip header time earlier than lowest-height block averaged*/
	long getnetworkhashps(int nblocks, int height);

	/**@return {@link NetworkInfo} data on the p2p network*/
	NetworkInfo getnetworkinfo();

	/**@see <a href="https://bitcoin.org/en/developer-reference#getnewaddress">getnewaddress wiki</a>
	 * @param label account label to put the address in
	 * @return a P2PKH address not in the wallet but mostly likely
	 * already in the keypool (visible through, ie, {@link #dumpwallet(String)}
	 * @throws JsonRpcClientException if this action depletes the keypool and the wallet is locked*/
	String getnewaddress(String label);
	String getnewaddress();

	/**@return P2PKH address not previously seen by the RPC.
	 * The address will be removed from the keypool but not marked as a receiving address,
	 * so RPCs such as the dumpwallet RPC will show it as a change address and may already
	 * have been exposed as part of the keypool, see {@link #dumpwallet(String)}
	 * @throws JsonRpcClientException if this action depletes the keypool and the wallet is locked*/
	String getrawchangeaddress();

	/**@return {@link PeerInfo} about each connected peer-to-peer rpc.*/
	List<PeerInfo> getpeerinfo();

	/**@return all tx ids in the mempool*/
	List<String> getrawmempool();

	/**@param yes must be true or use {@link #getrawmempool()} for a list of Txs
	 * @return tx ids mapped to objects describing each Tx in mempool*/
	Map<String, MempoolTx> getrawmempool(boolean yes);

	/** a hex-encoded serialized transaction, 'if found'.<br/>
	 * <b>'if found'</b>: By default, a fullnode only stores complete transaction data for UTXOs and your own transactions,
	 * so the call may fail on historic transactions unless you use the non-default txindex=1 in your fullnode startup settings.
	 * @see <a href="https://bitcoin.org/en/developer-reference#getrawtransaction">getrawtransaction wiki</a>
	 * @see #decoderawtransaction(String)
	 * @param hash
	 * @return hex string or null if not found
	 * @throws JsonRpcClientException hash not found*/
	String getrawtransaction(String hash);

	/**@see <a href="https://bitcoin.org/en/developer-reference#getrawtransaction">getrawtransaction wiki</a>
	 * @param hash tx_id of a TX to lookup
	 * @param verbose must be 1 or use (see {@link #getrawtransaction(String)}) for deserialized hex string of Tx
	 * @return a {@link VerboseTx} or null if not found in the rpc's database
	 * 		(see 'if found' section of {@link #getrawtransaction(String)})
	 * @throws RuntimeException if verbose param is not 1 (hex string, not json, returned by fullnode) */
	VerboseTx getrawtransaction(String hash, Integer verbose);

	/**@param account label
	 * @param confirmations 0 to count zeroconf, default: 1
	 * @return the amount received by addresses in an account with the specified confirmations*/
	BigDecimal getreceivedbyaccount(String account, int confirmations);
	BigDecimal getreceivedbyaccount(String account);

	/**@param address address of who's transactions to tally
	 * @param confirmations 0 to count zeroconf, default: 1
	 * @return the amount received by addresses in an account with the specified confirmations*/
	BigDecimal getreceivedbyaddress(String address, int confiramtions);
	BigDecimal getreceivedbyaddress(String address);

	/**@see <a href="https://bitcoin.org/en/developer-reference#gettransaction">gettransaction wiki</a>
	 * @param hash
	 * @param if false (the default) treat watch-only addresses as if they are not part of the wallet
	 * @return WalletTransaction for hash if the wallet was already tracking it (it is an in-wallet tx)
	 * @throws JsonRpcClientException if hash is not a Tx or node is not -txindex'd and hash is not an internal Wallet Tx*/
	WalletTx gettransaction(String hash, boolean includeWatchOnly);
	WalletTx gettransaction(String hash);

	/** only unspent txouts are guaranteed to be retrievable */
	TxOut gettxout(String txid, int vout, boolean includeZeroconf);

	/**A proof that one or more specified transactions were included in a block.<br/>
	 * NOTE: By default this function only works when there is an unspent output in the UTXO set for this transaction.
	 * @see <a href="https://bitcoin.org/en/developer-reference#gettxoutproof">gettxoutproof wiki</a>
	 * @param txids TXIDs of the transactions to generate proof for. All transactions must be in the same block
	 * @param header If specified, looks for txid in the block with this hash
	 * @return serialized, hex-encoded data for the proof
	 * @TODO test*/
	String gettxoutproof(List<String> txids, String header);

	/**@return statistics about the confirmed unspent transaction output (UTXO) set.
	 * Note that this call may take some time and that it only counts
	 * outputs from confirmed transactions—it does not count outputs from the memory pool. */
	TxOutsetInfo gettxoutsetinfo();

	/**@return balance of zeroconfs only*/
	BigDecimal getunconfirmedbalance();

	/**@return {@link WalletInfo} data on the wallet */
	WalletInfo getwalletinfo();

	/**@return help index*/
	String help();
	/**help on a specific topic*/
	String help(String topic);

	/**Adds an address or pubkey script to the wallet without the associated private key, allowing you to watch
	 * for transactions affecting that address or pubkey script without being able to spend any of its outputs.
	 * @param importData Either a P2PKH or P2SH address encoded in base58check, or a pubkey script encoded as hex
	 * @param account label to associate with, set to empty string for the default account
	 * @param rescan
	 * @TODO test*/
	void importaddress(String importData, String account, boolean rescan);

	/**Import a private key into your bitcoin wallet
	 * Private key must be in wallet import format <a href="https://bitcoin.org/en/developer-guide#wallet-import-format-wif">(WIF)</a>.
	 * @see #dumpprivkey(String)
	 * @requires unlocked wallet
	 * @param privKey
	 * @param account label for address (optional)
	 * @param rescan true to rescan the blockchain for the new key (can take some time)
	 * @throws JsonRpcClientException wallet locked, invalid key*/
	void importprivkey(String privKey, String account, boolean rescan);
	/**@see #importprivkey(String, String, boolean)*/
	void importprivkey(String privKey, String account);
	/**@see #importprivkey(String, String, boolean)*/
	void importprivkey(String privKey);

	/**Imports private keys from a file in wallet dump file format (see {@link #dumpwallet(String)}).
	 * These keys will be added to the keys currently in the wallet. This call may need to rescan
	 * @see <a href="https://bitcoin.org/en/developer-reference#importwallet">importwallet wiki</a>
	 * @requires unlocked wallet
	 * @param relativePath  Path relative to the bitcoind working directory
	 * @throws JsonRpcClientException locked wallet, file errors*/
	void importwallet(String relativePath);

	/**@see <a href="https://bitcoin.org/en/developer-reference#keypoolrefill">keypoolrefill wiki</a>
	 * @requires Unlocked wallet
	 * @param The new size of the keypool; if the number of keys in the keypool is less than this number,
	 * new keys will be generated. Default is 100. The value 0 also equals the default.
	 * The value specified is for this call only—the default keypool size is not changed*/
	void keypoolrefill(int count);

	Map<String, BigDecimal> listaccounts(int confirmations, boolean includeWatchOnly);
	Map<String, BigDecimal> listaccounts(int confirmations);
	/**@return Map of account name keys, account balance values.*/
	Map<String, BigDecimal> listaccounts();

	/**Strange JSON here, see the fullnodej-test WalletAccount.addressGroupings()
	 * @see <a href="https://bitcoin.org/en/developer-reference#listaddressgroupings">listaddressgroupings wiki</a>*/
	List<List<AddressDetails[]>> listaddressgroupings();

	/**@return a list of temporarily unspendable (locked) outputs*/
	List<Outpoint> listlockunspent();

	/**Lists the total number of bitcoins received by each account.
	  * @param minConfirmations confs an externally generated tx must have to count towards balance.
	  * @param includeEmpty Set to false (the default) to only include accounts which have received a payment.
	  * 	Any account which has received a payment will be displayed even if its current balance is 0
	  * @param includeWatchOnly
	  * @return Accounts: amount, confirmations, account, involvesWatchonly*/
	List<ReceivedByAccount> listreceivedbyaccount(long minConfirmations, boolean includeEmpty, boolean includeWatchOnly);

	/**@param minConfirmations
	 * @param includeEmpty
	 * @param includeWatchOnly
	 * @return List of "address" : receiving address, "account" : receiving address account , "amount" : total amount
	 * received by the address,"confirmations" : number of confirmations of the most recent transaction included */
	List<ReceivedByAddress> listreceivedbyaddress(long minConfirmations, boolean includeEmpty, boolean includeWatchOnly);

	/**get all transactions affecting the wallet which have occurred since a particular block
	 * @see <a href="https://bitcoin.org/en/developer-reference#listsinceblock">listsinceblock wiki</a>
	 * @param blockhash All transactions affecting the wallet which are not in that block or any earlier block returned,
	 * 		including unconfirmed transactions. default: genesis block
	 * @param depth (Untested) subtract from best block and return the hash as lastblock field in results.
	 * 	with this many confirmations. This does not affect which transactions are returned. Default is 1, so
	 * 	the hash of the most recent block on the local best block chain is returned
	 * @return a blockhash a a particular block some time ago
	 * 	and a list of wallet transactions that have taken place since the supplied blockhash */
	Catchup listsinceblock(String blockhash, Integer depth, Boolean includeWatchonly);
	Catchup listsinceblock();

	/**@see <a href="https://bitcoin.org/en/developer-reference#listtransactions">listtransactions wiki</a>
	 * @param account optional name of account, default/null: all accounts
	 * @param count how many results to return, default: 10
	 * @param offset how many recent results to skip (allows for pagination)
	 * @return Recent transactions (up to {@code count}, skipping {@code offset} for {@code account})*/
	List<WalletTx> listtransactions(String account, int count, int offset);
	List<WalletTx> listtransactions();
	List<WalletTx> listtransactions(String account);
	List<WalletTx> listtransactions(String account, int count);

	/**@see <a href="https://bitcoin.org/en/developer-reference#listunspent">listunpent wiki</a>
	 * @param minconf minimum confirmations to be included, default: 1
	 * @param maxconf maximum confirmation to be included, default: 10 million
	 * @param addresses addresses to filter for, default: none*/
	List<Utxo> listunpsent(int minconf, int maxconf, List<String> addresses);
	List<Utxo> listunspent(int minconf, int maxconf);
	List<Utxo> listunspent(int minconf);
	List<Utxo> listunspent();

	/**Temporarily locks or unlocks specified transaction outputs. A locked transaction output will not be
	 * chosen by automatic coin selection when spending bitcoins. Locks are stored in memory only, so nodes
	 * start with zero locked outputs and the locked output list is always cleared when a node stops or fails.
	 * @see <a href="https://bitcoin.org/en/developer-reference#listunspent">lockunspent wiki</a>
	 * @see #listlockunspent()
	 * @param lock Set to false to lock the outputs specified in the following parameter. Set to true
	 * to unlock the outputs specified.
	 * @param outputs the outputs to lock or unlock*/
	boolean lockunspent(boolean unlock, List<Outpoint> outputs);


	/**Move an amount from one account in your wallet to another in an off-block-chain transaction.<br/>
	 * Warning: it’s allowed to move more funds than are in an account (sending account negative and receiving account
	 * a balance that may exceed the number of bitcoins in the wallet (or the number of bitcoins in existence).
	 * @see <a href="https://bitcoin.org/en/developer-reference#move">move wiki</a>
	 * @param fromAccount
	 * @param toAccount
	 * @param amount
	 * @return true on success*/
	boolean move(String fromAccount, String toAccount, BigDecimal amount);
	boolean move(String fromAccount, String toAccount, BigDecimal amount, int unused, String comment);

	/**sends a P2P ping message to all connected nodes to measure ping time. Results are
	 * provided by the getpeerinfo RPC pingtime and pingwait fields as decimal seconds.*/
	void ping();

	/**@see <a href="https://bitcoin.org/en/developer-reference#prioritisetransaction">prioitisetransaction wiki</a>
	 * @param txid
	 * @param priorityOffset
	 * @param fee congrats, you get to use satoshis here and only here
	 * @return true if, congrats, you indeed provided all 3 params*/
	Boolean prioritisetransaction(String txid, BigDecimal priorityOffset, int fee);

	/**Spends an amount from a local account to a bitcoin address.
	 * @see <a href="https://bitcoin.org/en/developer-reference#prioritisetransaction">sendfrom wiki</a>
	 * @param fromAccount empty string for the default account
	 * @param bitcoinAddress
	 * @param amount
	 * @return txid */
	String sendfrom(String fromAccount, String bitcoinAddress, BigDecimal amount);
	String sendfrom(String fromAccount, String bitcoinAddress, BigDecimal amount, int minconf, String comment, String commentTo);

	/**Creates and broadcasts a transaction which sends outputs to multiple addresses.
	 * @param fromAccount empty string for the default account
	 * @param addressAmountPairs which addresses receive how much
	 * @return txid */
	String sendmany(String fromAccount, Map<String,BigDecimal> addressAmountPairs);
	String sendmany(String fromAccount, Map<String,BigDecimal> addressAmountPairs, int minconf, String comment);

	/**Validate a transaction and broadcast it to the peer-to-peer network.
	 * @param hex the tx to offer to other nodes, serialized
	 * @param allowHighFees see <a href="https://bitcoin.org/en/developer-reference#sendmany">the wiki</a>
	 * @return txid */
	String sendrawtransaction(String hex, boolean allowHighFees);

	/**@see <a href="https://bitcoin.org/en/developer-reference#sendtoaddress">sendtoaddress wiki</a>
	 * @param address bitcoin address
	 * @param amount in BTC rounded to 8 decimal places
	 * @return Transaction hash on success*/
	String sendtoaddress(String address, BigDecimal amount);
	/**@see {@link #sendtoaddress(String, BigDecimal)}
	 * @param comment (optional) locally stored in the fullnode (not broadcast) comment associated with the TX
	 * @param addressComment (optional) locally stored comment meant to describe who the payment was sent to. */
	String sendtoaddress(String address, BigDecimal amount, String comment, String addressComment);

	/**Moves the wallet address to a different account
	 * @see <a href="https://bitcoin.org/en/developer-reference#setaccount">setaccount wiki</a>
	 * @param address P2PKH or P2SH address to reassign (must already belong to the wallet)
	 * @param account label
	 * @throws JsonRpcClientException if private key for address not found in wallet*/
	void setaccount(String address, String account);

	/**Sets the transaction fee per kilobyte paid by transactions created by this wallet.
	 * @see <a href="https://bitcoin.org/en/developer-reference#settxfee">settxfee wiki</a>
	 * @see #estimatefee(int)
	 * @param fee The transaction fee to pay, in bitcoins, for each kilobyte of transaction data.
	 * Low fee paying transactions may not get included in blocks (quickly).
	 * @return true on success*/
	boolean settxfee(BigDecimal fee);

	/**this 3-param method on regtest only
	 * @see <a href="https://bitcoin.org/en/developer-reference#setgenerate">setgenerate wiki</a>
	 * @param generate true to start mining, false to stop
	 * @param processors how many cpus, -1 = all, default = 1
	 * @return genesis block(s) (regtest only)*/
	List<String> setgenerate (boolean generate, int processors, int nblocks);
	/**do not use on regtest*/
	void setgenerate (boolean generate, int processors);
	/**do not use on regtest*/
	void setgenerate (boolean generate);

	/**Sign a message with the private key of an address.<br/>
	 * Requires wallet support: an unlocked wallet or an unencrypted wallet.
	 * @see <a href="https://bitcoin.org/en/developer-reference#signmessage">signmessage wiki</a>
	 * @see #verifymessage(String, String, String)
	 * @param address P2PKH address whose private key belongs to the wallet
	 * @param message message to sign
	 * @return The signature of the message, encoded in base64.
	 * @throws JsonRpcClientException address not in wallet or wallet locked */
	String signmessage(String address, String message);

	/**Signs a transaction in the serialized transaction format using private keys stored in the wallet or provided in the call.
	 * @see <a href="https://bitcoin.org/en/developer-reference#signrawtransaction">signrawtransaction wiki</a>
	 * @param hex serialized transaction
	 * @param dependencies  previous outputs being spent by this transaction with scripts
	 * @param privateKeys if not provided and the wallet is available, keys from there may be used.
	 * @param sigHash The type of signature hash to use for all of the signatures performed. (You must use separate
	 * calls to the signrawtransaction RPC if you want to use different signature hash types for different signatures.
	 * @return signed transaction
	 * @TODO test*/
	SignedTransaction signrawtransaction(String hex, List<Dependency> dependencies, List<String> privateKeys, SigHash sigHash);

	/** shutdown the actual bitcoind full rpc
	 * @return rpc sends "Bitcoin server stopping"*/
	String stop();

	/**@see <a href="https://bitcoin.org/en/developer-reference#validateaddress">validateaddress wiki</a><br/>
	 * @param bitcoinAddress
	 * @return information about the bitcoin address.*/
	AddressInformation validateaddress(String bitcoinAddress);

	/**@see <a href="https://bitcoin.org/en/developer-reference#verifychain">verifychain wiki</a><br/>
	 * @param checkLevel verification depth, 0 (lowest) to 4 (highest)
	 * @param blockCount number of blocks to check, 0 = all
	 * @return false if verification failed for any reason.*/
	boolean verifychain(int checkLevel, int blockCount);

	/**Verifies a signed message.
	 * @see <a href="https://bitcoin.org/en/developer-reference#verifymessage">verifymessage wiki</a>
	 * @see #signmessage(String, String)
	 * @param bitcoinaddress
	 * @param signature
	 * @param message
	 * @return true if the signature and message matches the bitcoinaddress provided
	 * @throws JsonRpcClientException for some illegal signatures */
	boolean verifymessage(String bitcoinaddress, String signature, String message);

	/**verifies that a proof points to one or more transactions in a block, returning the transactions
	 * the proof commits to and throwing an RPC error if the block is not in our best block chain.
	 * @see <a href="https://bitcoin.org/en/developer-reference#verifytxoutproof">verifytxoutproof wiki</a>
	 * @param proof hex-enceded proof
	 * @return txid(s) which the proof commits to, or empty list if the proof is invalid*/
	List<String> verifytxoutproof(String proof);

	/**@see <a href="https://bitcoin.org/en/developer-reference#submitblock">submitblock wiki</a>
	 * @param blockhex complete block
	 * @return null validation passed, otherwise duplicate|duplicate-invalid|inconclusive|rejected
	 * @TODO test*/
	String submitblock(String blockhex);

	/**Removes the wallet encryption key from memory, locking the wallet. After calling this method, you will need to call
	 * walletpassphrase again before being able to call any methods which require the wallet to be unlocked.*/
	void walletlock();

	/**Stores the wallet decryption key in memory for the indicated number of seconds.
	 * Issuing the walletpassphrase command while the wallet is already unlocked will set a new unlock time that overrides the old one.
	 * <br/><br/>Warning: security for this field is needed in production environments (care of any .properties file, logging, persistence, etc).
	 * @see <a href="https://bitcoin.org/en/developer-reference#walletpassphrase">walletpassphrase wiki</a>
	 * @see #walletlock()
	 * @see #walletpassphrasechange()
	 * @param passphrase
	 * @param seconds_open
	 * @throws JsonRpcClientException on invalid passphrase */
	void walletpassphrase(String passphrase, int seconds_open);

	/**@see <a href="https://bitcoin.org/en/developer-reference#walletpassphrase">walletpassphrasechange wiki</a>
	 * @see #encryptwallet(String)
	 * @see #walletpassphrase(String, int)
	 * @param oldPassphrase
	 * @param newPassphrase
	 * @throws JsonRpcClientException if current is invalid*/
	void walletpassphrasechange(String oldPassphrase, String newPassphrase);

}
