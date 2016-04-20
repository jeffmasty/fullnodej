package org.fullnodej.data;

import lombok.Data;

@Data
public class SoftforkPolicy {

	boolean status;
	int found;
	int required;
	int window;

}
