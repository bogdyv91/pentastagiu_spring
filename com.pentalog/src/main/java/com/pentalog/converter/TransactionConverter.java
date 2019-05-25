package com.pentalog.converter;

import org.springframework.stereotype.Component;

import com.pentalog.dto.TransactionDTO;
import com.pentalog.model.Transaction;

/**
 * Contains method to convert from Transaction to its data transfer object Transaction
 * 
 * @author Vacariuc Bogdan
 *
 */
@Component
public class TransactionConverter {

	/**
	 * Converts to TransactionDTO
	 * @param transaction the transaction that has to be converted
	 * @return data transfer object of given parameter
	 */
	
	public TransactionDTO convertToTransactionDTO(Transaction transaction) {
		return new TransactionDTO(transaction.getAccount(), transaction.getAmount(), transaction.getDetails(),
				transaction.getAccountId().getAccountNumber());
	}
}
