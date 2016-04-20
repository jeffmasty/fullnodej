package org.fullnodej.data;

import lombok.Data;

@Data
public class Softfork {

	String id;
	int version;
	SoftforkPolicy enforce;
	SoftforkPolicy reject;

}
