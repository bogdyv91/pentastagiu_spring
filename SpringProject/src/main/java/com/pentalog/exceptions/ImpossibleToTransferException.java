package com.pentalog.exceptions;

/**
 * This exception is thrown when there occurs a failed transfer.
 * 
 * @author Vacariuc Bogdan
 *
 */
public class ImpossibleToTransferException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ImpossibleToTransferException(String message) {
		super(message);
	}

}
