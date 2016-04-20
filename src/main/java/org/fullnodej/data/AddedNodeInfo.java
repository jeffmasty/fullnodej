package org.fullnodej.data;

import java.util.List;

import lombok.Data;

/**@see <a href="">getaddednodeinfo wiki</a>
 *
 */
@Data
public class AddedNodeInfo {

	String addednode;
	boolean connected;
	/**If the Details parameter was set to true, this will be
	 * an array of addresses belonging to the added node*/
	List<AddedNodeAddressInfo> addresses;

}
