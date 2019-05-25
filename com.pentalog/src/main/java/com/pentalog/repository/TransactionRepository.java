package com.pentalog.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.pentalog.model.Account;
import com.pentalog.model.Transaction;

/**
 * Repository that contains queries to the transaction table
 * 
 * @author Vacariuc Bogdan
 *
 */

@Component
public interface TransactionRepository extends Repository<Transaction, Long> {

	public List<Transaction> findByAccountId(Account account);

	public void save(Transaction transaction);
}
