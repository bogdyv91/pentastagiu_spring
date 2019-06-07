package com.pentalog.controller;

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
import com.pentalog.model.User;
import com.pentalog.service.AccountService;
import com.pentalog.service.AuthentificationService;
import com.pentalog.service.NotificationService;
import com.pentalog.service.TransactionService;

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

	@Autowired
	NotificationService notificationService;

	/**
	 * Get http request used to get a list containing transactions for the user
	 * corresponding to the requested parameter token
	 * 
	 * @param token generated token for authentification table and further checks
	 * @return list of data transfer objects of data transfer object Transaction
	 */

	@SuppressWarnings("static-access")
	@GetMapping
	public ResponseEntity<List<TransactionDTO>> showTransactions(@RequestParam(value = "token") String token) {
		Optional<User> user = authentificationService.getUserByToken(token);
		return new ResponseEntity<List<TransactionDTO>>(HttpStatus.ACCEPTED)
				.of(Optional.of(transactionService.getTransactionsByUser(user.get())));
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
		transactionService.makeNewTransaction(transaction);
		return ResponseEntity.ok(null);
	}

}
