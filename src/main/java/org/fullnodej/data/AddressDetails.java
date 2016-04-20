package org.fullnodej.data;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AddressDetails {

	/**Base58 address*/
	String Address;

	/**Current spendable balance of the address, not counting unconfirmed transactions*/
	BigDecimal Balance;

	/**account the address belongs to, if any. This field will not be returned for change addresses.
	 * The default account is an empty string*/
	String Account;

	public AddressDetails(String address) {
		this.Address = address;
	}

	public AddressDetails(BigDecimal balance) {
		this.Balance = balance;
	}

	public AddressDetails(double balance) {
		this.Balance = new BigDecimal(balance);
	}

	public AddressDetails(String address, BigDecimal balance) {
		this.Address = address;
		this.Balance = balance;
	}

	@Override
	public String toString() {
		String result = "";
		if (Address != null) result += Address + " ";
		if (Balance != null) result += Balance;
		if (Account != null) result += " " + Account;
		return result;
	}
}
