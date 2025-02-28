package com.pentalog.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.pentalog.model.Account;
import com.pentalog.model.User;

/**
 * Repository that contains queries to the account table
 * 
 * @author Vacariuc Bogdan
 *
 */

@Component
public interface AccountRepository extends Repository<Account, Long> {

	public List<Account> findByAccountNumber(String accountNumber);

	public List<Account> findByUser(User user);

	public void save(Account account);
}
