package org.fullnodej.data;

import java.math.BigDecimal;
import java.util.HashSet;

import lombok.Data;

@Data
public class NetworkInfo {

	long version;
	String subversion;
	long protocolversion=70002;
	long localservices;
	long timeoffset;
	int connections;
	HashSet<Network> networks;
    BigDecimal relayfee;
    HashSet<LocalAddress> localaddresses;

}
