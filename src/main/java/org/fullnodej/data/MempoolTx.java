package org.fullnodej.data;

import java.math.BigDecimal;
import java.util.List;

import org.fullnodej.Rpc;

import lombok.Data;

/**a description of a Tx in the mempool.
 * @see Rpc#getrawmempool()*/
@Data
public class MempoolTx {

	int size;
	BigDecimal fee;
	long time;
	int height;
	int startingpriority;
	int currentpriority;
	List<String> depends;

}
