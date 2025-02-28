package com.pentalog.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pentalog.converter.AccountConverter;
import com.pentalog.dto.AccountDTO;
import com.pentalog.exceptions.ImpossibleToInsertAccountException;
import com.pentalog.model.Account;
import com.pentalog.model.User;
import com.pentalog.repository.AccountRepository;
import com.pentalog.utilities.AccountValidations;

/**
 * Service class for operations with Account objects
 * 
 * @author Vacariuc Bogdan
 *
 */

@Service
public class AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountConverter accountConverter;

	/**
	 * This method returns the account with the given account number
	 * 
	 * @param accountNumber the account number of the account that is searched into
	 *                      the account table
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
	 * This methord returns a list of AccountDTO for the specified user
	 * 
	 * @param user
	 * @return list of AccountDTO
	 */
	public List<AccountDTO> getAccountsDTOForUser(User user) {
		List<Account> accounts = accountRepository.findByUser(user);
		List<AccountDTO> accountsDTO = new LinkedList<AccountDTO>();
		accounts.stream().forEach(x -> accountsDTO.add(AccountConverter.convertToAccountDTO(x)));
		return accountsDTO;
	}

	/**
	 * Saves an account in the database
	 * 
	 * @param account the account that is saved into the database
	 */

	public void saveAccountByDTOAndUser(AccountDTO accountDTO, User user) {
		Optional<Account> account = this.getAccountByAccountNumber(accountDTO.getAccountNumber());
		if (!account.isPresent()) {
			Account accountToInsert = new Account(accountDTO.getAccountNumber(), user, accountDTO.getBalance(),
					accountDTO.getAccountType());
			if (AccountValidations.accountNumberValidation(accountDTO.getAccountNumber())) {
				accountRepository.save(accountToInsert);
			} else {
				LOGGER.error("Incorrect account details received!");
				throw new ImpossibleToInsertAccountException("Incorrect account details received");
			}
		} else {
			LOGGER.error("Account already exists in database!");
			throw new ImpossibleToInsertAccountException("Account already exists in database");
		}
	}

}
