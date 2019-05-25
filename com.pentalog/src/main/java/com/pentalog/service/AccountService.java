package com.pentalog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pentalog.model.Account;
import com.pentalog.model.User;
import com.pentalog.repository.AccountRepository;

/**
 * Service class for operations with Account objects
 * 
 * @author Vacariuc Bogdan
 *
 */

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	
	/**
	 * This method returns the account with the given account number
	 * 
	 * @param accountNumber the account number of the account that is searched into the account table
	 * @return Optional of Account
	 */
	public Optional<Account> getAccountByAccountNumber(String accountNumber) {
		List<Account> accounts = accountRepository.findByAccountNumber(accountNumber);
		if (accounts.size() == 1) {
			return Optional.ofNullable(accounts.get(0));
		}
		return Optional.empty();
	}

	
	/**
	 * Gets a list of accounts for the specified user
	 * 
	 * @param user the user that has the accounts that are searched for
	 * @return List of accounts that correspond to the given user
	 */
	public List<Account> getAccounts(User user) {
		return accountRepository.findByUser(user);
	}

	/**
	 * Saves an account in the database
	 * 
	 * @param account the account that is saved into the database
	 */
	
	public void save(Account account) {
		accountRepository.save(account);
	}
}
