package org.fullnodej.data;

import java.net.InetAddress;

import lombok.Data;

@Data
public class LocalAddress {

	InetAddress address;
	int port;
	int score;

}
