package com.pentalog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pentalog.model.Account;
import com.pentalog.model.Transaction;
import com.pentalog.repository.TransactionRepository;

/**
 * Service class for operations with Transaction objects
 * 
 * @author Vacariuc Bogdan
 *
 */

@Service
public class TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	/**
	 * Saves a transaction in the database
	 * 
	 * @param transaction the transaction that is saved
	 */
	public void save(Transaction transaction) {
		transactionRepository.save(transaction);
	}

	/**
	 * Gets a list of transactions for the specified account
	 * 
	 * @param account the account that transactions are searched by
	 * @return List of transactions
	 */
	public List<Transaction> findByAccountId(Account account) {
		return transactionRepository.findByAccountId(account);
	}
}
