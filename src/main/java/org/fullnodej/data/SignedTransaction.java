package org.fullnodej.data;

import lombok.Data;

@Data
public class SignedTransaction {

	/** serialized transaction with signatures (possibly zero signatures)*/
	String hex;

	/** what say ye?*/
	boolean complete;

}
