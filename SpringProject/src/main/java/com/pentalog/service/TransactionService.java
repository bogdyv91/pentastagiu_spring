package com.pentalog.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pentalog.converter.TransactionConverter;
import com.pentalog.dto.TransactionDTO;
import com.pentalog.model.Account;
import com.pentalog.model.Notification;
import com.pentalog.model.Transaction;
import com.pentalog.model.User;
import com.pentalog.repository.AccountRepository;
import com.pentalog.repository.NotificationRepository;
import com.pentalog.repository.TransactionRepository;
import com.pentalog.utilities.AccountValidations;
import com.pentalog.utilities.enums.Type;

/**
 * Service class for operations with Transaction objects
 * 
 * @author Vacariuc Bogdan
 *
 */

@Service
public class TransactionService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	TransactionConverter transactionConverter;

	@Autowired
	NotificationRepository notificationRepository;

	/**
	 * Saves a transaction in the database
	 * 
	 * @param transaction the transaction that is saved
	 */

	public void makeNewTransaction(TransactionDTO transaction) {
		Account accountFrom = accountService.getAccountByAccountNumber(transaction.getAccountFrom()).orElse(null);
		Account accountTo = accountService.getAccountByAccountNumber(transaction.getAccountTo()).orElse(null);
		if (AccountValidations.isTransferable(accountFrom, accountTo, transaction.getAmount())) {
			accountFrom.setBalance(accountFrom.getBalance().subtract(transaction.getAmount()));
			accountTo.setBalance(accountTo.getBalance().add(transaction.getAmount()));
			accountRepository.save(accountFrom);
			accountRepository.save(accountTo);

			Transaction transactionTo = new Transaction(transaction.getAccountTo(), transaction.getAmount(),
					transaction.getDetails(), Type.OUTGOING, accountFrom);
			Transaction transactionFrom = new Transaction(transaction.getAccountFrom(), transaction.getAmount(),
					transaction.getDetails(), Type.INCOMING, accountTo);

			transactionRepository.save(transactionTo);
			transactionRepository.save(transactionFrom);

			notificationRepository.save(new Notification(accountFrom.getUser(), transactionTo));
			notificationRepository.save(new Notification(accountTo.getUser(), transactionFrom));
		}
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

	/**
	 * Gets a list of TransactionDTO for the specified user
	 * 
	 * @param user
	 * @return
	 */
	public List<TransactionDTO> getTransactionsByUser(User user) {
		if (user == null) {
			return new ArrayList<TransactionDTO>();
		}
		List<Account> accounts = accountRepository.findByUser(user);
		List<TransactionDTO> transactionsDTO = new LinkedList<>();
		accounts.stream().forEach(x -> this.findByAccountId(x).stream()
				.forEach(y -> transactionsDTO.add(TransactionConverter.convertToTransactionDTO(y))));
		return transactionsDTO;
	}
}
