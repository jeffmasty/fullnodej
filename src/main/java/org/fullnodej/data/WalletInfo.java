package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WalletInfo {

	int walletversion;
	BigDecimal balance;
	BigDecimal unconfirmed_balance;
	BigDecimal immature_balance;
	long txcount;
	long keypoololdest;
	int keypoolsize;
	/** 0 of currently locked, or date in the future if currently unlocked, null if wallet is unencrypted.*/
	Long unlocked_until;
	/**@return null if wallet is unencrypted, 0 of locked, or date in the future if currently unlocked*/
	public Long getUnlocked_until() { return unlocked_until; }

}
