package org.fullnodej.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Outpoint {

	/**txid of the outpoint in hex*/
	protected String txid;
	/**Output index number of the output*/
	protected int vout;

}
