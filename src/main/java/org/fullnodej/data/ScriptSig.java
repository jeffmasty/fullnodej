package org.fullnodej.data;

import lombok.Data;

@Data
public class ScriptSig {

	/**The signature script in decoded form with non-data-pushing opcodes listed*/
	protected String asm;
	/**The signature script encoded as hex*/
	protected String hex;

}
