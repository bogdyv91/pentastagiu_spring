package com.pentalog.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pentalog.utilities.enums.Currency;

/**
 * Model class for Account
 * 
 * @author Vacariuc Bogdan
 *
 */
@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "account_type")
	private Currency accountType;

	@Column(name = "created_time")
	private LocalDateTime createdTime;

	@Column(name = "updated_time")
	private LocalDateTime updatedTime;

	/**
	 * Constructor without parameters. Only sets createdTime and updatedTime.
	 */
	public Account() {
		setTime();
	}

	/**
	 * Constructor with parameters.
	 * 
	 * @param accountNumber
	 * @param loggedUser
	 * @param balance
	 * @param currency
	 */
	public Account(String accountNumber, User loggedUser, BigDecimal balance, Currency currency) {
		// TODO Auto-generated constructor stub
		setTime();
		this.user = loggedUser;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.accountType = currency;
	}

	private void setTime() {
		createdTime = LocalDateTime.now();
		updatedTime = LocalDateTime.now();
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
		updatedTime = LocalDateTime.now();
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
		updatedTime = LocalDateTime.now();
	}

	public Currency getAccountType() {
		return accountType;
	}

	public void setAccountType(Currency accountType) {
		this.accountType = accountType;
		updatedTime = LocalDateTime.now();
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (accountType != other.accountType)
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
