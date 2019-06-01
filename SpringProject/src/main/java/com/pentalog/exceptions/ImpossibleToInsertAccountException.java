package com.pentalog.exceptions;

/**
 * This exception is thrown when there occurs a failed attempt to insert a new
 * account.
 * 
 * @author Vacariuc Bogdan
 *
 */
public class ImpossibleToInsertAccountException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ImpossibleToInsertAccountException(String message) {
		super(message);
	}

}