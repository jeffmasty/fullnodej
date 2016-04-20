package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Info {

	private long version;
	private long protocolversion;
	private long walletversion;
	private BigDecimal balance;
	private long blocks;
	private long timeoffset;
	private long connections;
	private String proxy;
	private BigDecimal difficulty;
	private boolean testnet;
	private long keypoololdest;
	private long keypoolsize;
	private BigDecimal paytxfee;
	private BigDecimal relayfee;
	private String errors;
	private long unlocked_until;

}
