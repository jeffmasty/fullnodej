package org.fullnodej.data;

import lombok.Data;

@Data
public class ReceivedByAccount {

	protected String amount;
    protected long confirmations;
    protected String account;
    protected boolean involvesWatchonly;

}
