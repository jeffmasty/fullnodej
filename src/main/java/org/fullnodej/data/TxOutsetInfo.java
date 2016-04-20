package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TxOutsetInfo {

	/**height of the local best block chain. A new node with only the hardcoded genesis block will have a height of 0*/
	int height;
	/**hex of the hash of the header of the highest block on the local best block chain*/
	String bestblock;

	/**number of transactions with unspent outputs*/
	int transactions;

	/**number of unspent transaction outputs*/
	int txouts;

	/**size of the serialized UTXO set in bytes; not counting overhead, this is the size of the
	 * chainstate directory in the Bitcoin Core configuration directory*/
	int bytes_serialized;

	/**hex of SHA256(SHA256()) hash of the serialized UTXO set; useful for comparing two nodes to see if they have the
	 * same set (they should, if they always used the same serialization format and currently have the same best block).*/
	String hash_serialized;

	/**total number of bitcoins in the UTXO set*/
	BigDecimal total_amount;

}
