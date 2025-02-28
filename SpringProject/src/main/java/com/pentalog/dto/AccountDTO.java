package com.pentalog.dto;

import java.math.BigDecimal;

import com.pentalog.utilities.enums.Currency;

/**
 * Data transfer object class for the model Account
 * 
 * @author Vacariuc Bogdan
 *
 */

public class AccountDTO {

	private String accountNumber;

	private Currency accountType;

	private BigDecimal balance;

	public AccountDTO(String accountNumber, Currency accountType, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Currency getAccountType() {
		return accountType;
	}

	public void setAccountType(Currency accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
