package com.pentalog.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Custom exception used for the rest controller advice
 * 
 * @author Vacariuc Bogdan
 * @see RestControllerAdvice
 */

public class CustomException {

	private String message;

	private HttpStatus status;

	public CustomException(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
