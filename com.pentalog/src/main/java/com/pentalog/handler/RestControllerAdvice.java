package com.pentalog.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pentalog.exceptions.CustomException;

/**
 * Controller advice class
 * 
 * @author Vacariuc Bogdan
 *
 */

@ControllerAdvice
public class RestControllerAdvice {

	/**
	 * This method handles all exceptions that occur at the runtime
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomException> handleAllExceptions(HttpServletRequest request, Exception ex) {
		return new ResponseEntity<>(
				new CustomException("An error occurred. Please try again", HttpStatus.INTERNAL_SERVER_ERROR),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
