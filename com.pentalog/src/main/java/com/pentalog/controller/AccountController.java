package com.pentalog.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pentalog.converter.AccountConverter;
import com.pentalog.dto.AccountDTO;
import com.pentalog.model.Account;
import com.pentalog.model.User;
import com.pentalog.service.AccountService;
import com.pentalog.service.AuthentificationService;
import com.pentalog.utilities.AccountValidations;

/**
 * Controller class for services using accounts
 * 
 * @author Vacariuc Bogdan
 *
 */

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	AccountConverter accountConverter;

	@Autowired
	AuthentificationService authentificationService;

	/**
	 * Get http request used to get user's accounts corresponding to the requested
	 * parameter token
	 * 
	 * @param token generated token for authentification table and further checks
	 * @return list of data transfer objects of Account
	 */
	@GetMapping
	public ResponseEntity<List<AccountDTO>> showAccountsForUser(@RequestParam(value = "token") String token) {
		Optional<User> user = authentificationService.getUserByToken(token);
		if (user.isPresent()) {
			List<Account> accounts = accountService.getAccounts(user.get());
			List<AccountDTO> accountsDTO = new LinkedList<AccountDTO>();
			for (Account account : accounts) {
				AccountDTO accountDTO = accountConverter.convertToAccountDTO(account);
				accountsDTO.add(accountDTO);
			}
			new ResponseEntity<List<AccountDTO>>(HttpStatus.ACCEPTED);
			return ResponseEntity.ok(accountsDTO);
		}
		return new ResponseEntity<List<AccountDTO>>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Post http request used to create a new account for the user that corresponds
	 * to the requested parameter token
	 * 
	 * 
	 * @param token      token generated token for authentification table and
	 *                   further checks
	 * @param accountDTO requested informations for creating a new Account
	 * @return data transfer object of created Account
	 */

	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<AccountDTO> createAccount(@RequestParam(value = "token") String token,
			@RequestBody AccountDTO accountDTO) {
		Optional<User> user = authentificationService.getUserByToken(token);
		if (user.isPresent()) {
			Optional<Account> account = accountService.getAccountByAccountNumber(accountDTO.getAccountNumber());
			if (!account.isPresent()) {
				Account accountToInsert = new Account(accountDTO.getAccountNumber(), user.get(),
						accountDTO.getBalance(), accountDTO.getAccountType());
				if (AccountValidations.accountNumberValidation(accountDTO.getAccountNumber())) {
					accountService.save(accountToInsert);
					new ResponseEntity<AccountDTO>(HttpStatus.ACCEPTED);
					return ResponseEntity.ok(accountConverter.convertToAccountDTO(accountToInsert));
				}
			}
		}
		return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
	}
}
