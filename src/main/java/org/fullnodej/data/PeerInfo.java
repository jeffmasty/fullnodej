package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.Data;

/**data about each connected network node.
 * @see <a href="https://bitcoin.org/en/developer-reference#getpeerinfo">getpeerinfo wiki</a>*/
@Data
public class PeerInfo {

	/**index number in the local node address database.<br/>Added in Bitcoin Core 0.10.0*/
	int id;
	/**IP address and port to the remote node*/
	String addr;
	/**Our IP address and port according to the remote node. May be incorrect due to error or lying. Many SPV nodes set this to 127.0.0.1:8333*/
	String addrlocal;
	/** hex string of services advertised by the remote node in its version message*/
	String services;
	long lastsend;
	long lastrecv;
	long bytessent;
	long bytesrecv;
	long conntime;
	long timeoffset;
	BigDecimal pingtime;
	BigDecimal pingwait;

	int version;
	String subver;
	boolean inbound;
	int startingheight;
	int banscore;
	int synced_headers;
	int synced_blocks;
	/**array of blocks which have been requested from this peer. May be empty<br/>Added in Bitcoin Core 0.10.0*/
	String[] inflight;
	boolean whitelisted;

}
/*{id=1, addr=123.456.789.1:18333, addrlocal=101.10.101.2:36059, services=0000000000000001, lastsend=1460312403,
 * lastrecv=1460312499, bytessent=400668, bytesrecv=1581891, conntime=1460155988, timeoffset=0, pingtime=0.161459, version=70002,
 * subver=/Satoshi:0.11.2/, inbound=false, startingheight=763572, banscore=0, synced_headers=764451, synced_blocks=764451,
 * inflight=[], whitelisted=false}*/