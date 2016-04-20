package org.fullnodej.data;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ReceivedByAddress {

    private BigDecimal amount;
    private long confirmations;
    private String address;
    private String account;
    boolean involvedWatchonly;
    List<String> txids;

}
