package org.fullnodej.data;

import lombok.Data;

@Data
public class AddedNodeAddressInfo {

	/* ipAddress:port, if resolvable, the actual IP address */
	String address;
	/* true|false|inbound|outbound */
	String connected;
}
