package com.pentalog.controller;

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
import com.pentalog.model.User;
import com.pentalog.service.AccountService;
import com.pentalog.service.AuthentificationService;

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
	@SuppressWarnings("static-access")
	@GetMapping
	public ResponseEntity<List<AccountDTO>> showAccountsForUser(@RequestParam(value = "token") String token) {
		Optional<User> user = authentificationService.getUserByToken(token);
		List<AccountDTO> accountsDTO = accountService.getAccountsDTOForUser(user.get());
		return new ResponseEntity<List<AccountDTO>>(HttpStatus.ACCEPTED).of(Optional.of(accountsDTO));
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

	@SuppressWarnings("static-access")
	@PostMapping(produces = "application/json", consumes = "application/json")
	public ResponseEntity<AccountDTO> createAccount(@RequestParam(value = "token") String token,
			@RequestBody AccountDTO accountDTO) {
		Optional<User> user = authentificationService.getUserByToken(token);
		accountService.saveAccountByDTOAndUser(accountDTO, user.get());
		return new ResponseEntity<List<AccountDTO>>(HttpStatus.ACCEPTED).of(Optional.of(accountDTO));

	}
}
