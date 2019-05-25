package com.pentalog.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pentalog.converter.TransactionConverter;
import com.pentalog.dto.TransactionDTO;
import com.pentalog.exceptions.ImpossibleToTransferException;
import com.pentalog.model.Account;
import com.pentalog.model.Transaction;
import com.pentalog.model.User;
import com.pentalog.service.AccountService;
import com.pentalog.service.AuthentificationService;
import com.pentalog.service.TransactionService;
import com.pentalog.utilities.AccountValidations;
import com.pentalog.utilities.Type;

/**
 * Controller class for services using transactions
 * 
 * @author Vacariuc Bogdan
 *
 */

@RestController
@RequestMapping("/transfer")
public class TransferController {

	@Autowired
	TransactionService transactionService;

	@Autowired
	TransactionConverter transactionConverter;

	@Autowired
	AuthentificationService authentificationService;

	@Autowired
	AccountService accountService;

	/**
	 * Get http request used to get a list containing transactions for the user
	 * corresponding to the requested parameter token
	 * 
	 * @param token generated token for authentification table and further checks
	 * @return list of data transfer objects of data transfer object Transaction
	 */

	@GetMapping
	public ResponseEntity<List<TransactionDTO>> showTransactions(@RequestParam(value = "token") String token) {
		Optional<User> user = authentificationService.getUserByToken(token);
		if (user.isPresent()) {
			List<Account> accounts = accountService.getAccounts(user.get());
			List<TransactionDTO> transactionsDTO = new LinkedList<>();
			for (Account account : accounts) {
				List<Transaction> transactions = transactionService.findByAccountId(account);
				for (Transaction transaction : transactions) {
					transactionsDTO.add(transactionConverter.convertToTransactionDTO(transaction));
				}
			}
			new ResponseEntity<List<TransactionDTO>>(HttpStatus.ACCEPTED);
			return ResponseEntity.ok(transactionsDTO);
		}
		return new ResponseEntity<List<TransactionDTO>>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param token       token generated token for authentification table and
	 *                    further checks
	 * @param transaction requested informations for making a new transaction
	 * @return returns BAD_REQUEST if the transaction could not be made, or ACCEPTED
	 *         if the transaction is done
	 */

	@PutMapping
	public ResponseEntity<?> makeTransaction(@RequestParam(value = "token") String token,
			@RequestBody TransactionDTO transaction) {
		try {
			if (authentificationService.getUserByToken(token).isPresent()) {
				Account accountFrom = accountService.getAccountByAccountNumber(transaction.getAccountFrom())
						.orElse(null);
				Account accountTo = accountService.getAccountByAccountNumber(transaction.getAccountTo()).orElse(null);
				if (AccountValidations.isTransferable(accountFrom, accountTo, transaction.getAmount())) {
					accountFrom.setBalance(accountFrom.getBalance().subtract(transaction.getAmount()));
					accountTo.setBalance(accountTo.getBalance().add(transaction.getAmount()));
					accountService.save(accountFrom);
					accountService.save(accountTo);
					transactionService.save(new Transaction(transaction.getAccountTo(), transaction.getAmount(),
							transaction.getDetails(), Type.OUTGOING, accountFrom));
					transactionService.save(new Transaction(transaction.getAccountFrom(), transaction.getAmount(),
							transaction.getDetails(), Type.INCOMING, accountTo));
					return ResponseEntity.accepted().build();
				}
			}
		} catch (ImpossibleToTransferException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.badRequest().build();
	}

}
