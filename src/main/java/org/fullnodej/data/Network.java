package org.fullnodej.data;

import lombok.Data;

@Data
public class Network {

	String name;
	boolean limited;
	boolean reachable;
	String proxy;
	boolean proxy_randomize_credentials;

}
