package org.fullnodej.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**see <a href="https://bitcoin.org/en/developer-reference#getchaintips">getchaintips wiki</a>*/
@RequiredArgsConstructor @Getter
public enum ChainTipStatus {

	active("active"),
	invalid("invalid"),
	headersOnly("headers-only"),
	validHeaders("valid-headers"),
	validFork("valid-fork"),
	unkown("unkown");

	private final String value;
	@Override
	public String toString() {
		return value;
	}

}
