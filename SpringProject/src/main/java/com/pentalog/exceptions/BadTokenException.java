package com.pentalog.exceptions;

public class BadTokenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This exception is throws when a received token is not found in the database
	 * 
	 * @param message
	 */
	public BadTokenException(String message) {
		super(message);
	}
}
