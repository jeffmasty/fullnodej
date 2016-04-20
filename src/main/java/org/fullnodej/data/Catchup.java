package org.fullnodej.data;

import java.util.List;

import lombok.Data;

@Data
public class Catchup {

	/** supposedly, the header hash of the block at the requested depth */
	private String lastblock;
	/** transactions affecting the wallet since the requested block */
	private List<WalletTx> transactions;

}
