package com.pentalog.dto;

import java.math.BigDecimal;

/**
 * Data transfer object class for the model Transaction
 * 
 * @author Vacariuc Bogdan
 *
 */

public class TransactionDTO {

	private String accountFrom;

	private BigDecimal amount;

	private String details;

	private String accountTo;

	public TransactionDTO(String accountFrom, BigDecimal amount, String details, String accountTo) {
		this.accountFrom = accountFrom;
		this.amount = amount;
		this.details = details;
		this.accountTo = accountTo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}

	public String getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}
}
