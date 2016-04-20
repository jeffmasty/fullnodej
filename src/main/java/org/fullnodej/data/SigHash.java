package org.fullnodej.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SigHash {

 	ALL("ALL"),
	NONE("NONE"),
	SINGLE("SINGLE"),
	ALL_ANYONECANPAY("ALL|ANYONECANPAY"),
	NONE_ANYONECANPAY("NONE|ANYONECANPAY"),
	SINGLE_ANYONECANPAY("SINGLE|ANYONECANPAY");

	private final String value;

	/** Json Mapper directed to use the toString() value */
	@Override
	public String toString() {
		return value;
	};

}
