package com.pentalog.converter;

import org.springframework.stereotype.Component;

import com.pentalog.dto.AccountDTO;
import com.pentalog.model.Account;

/**
 * Contains method to convert from Account to its data transfer object Account
 * 
 * @author Vacariuc Bogdan
 *
 */

@Component
public class AccountConverter {

	/**
	 * Converts to AccountDTO
	 * @param account the account that has to be converted
	 * @return data transfer object of given parameter
	 */
	
	public static AccountDTO convertToAccountDTO(Account account) {
		return new AccountDTO(account.getAccountNumber(), account.getAccountType(), account.getBalance());
	}
}
