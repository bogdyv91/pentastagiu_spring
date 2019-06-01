package com.pentalog.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pentalog.exceptions.BadTokenException;
import com.pentalog.exceptions.CustomException;
import com.pentalog.exceptions.ImpossibleToInsertAccountException;
import com.pentalog.exceptions.ImpossibleToTransferException;

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

	/**
	 * This method handles only ImpossibleToInsertAccountException exception
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ImpossibleToInsertAccountException.class)
	public ResponseEntity<CustomException> handleImpossibleToInsertAccountException(HttpServletRequest request,
			ImpossibleToInsertAccountException ex) {
		return new ResponseEntity<>(
				new CustomException("Could not insert specified account into database. Please try again.",
						HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method handles only ImpossibleToTransferException exception
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ImpossibleToTransferException.class)
	public ResponseEntity<CustomException> handleImpossibleToTransferException(HttpServletRequest request,
			ImpossibleToTransferException ex) {
		return new ResponseEntity<>(new CustomException("Could not make the specified transaction. Please try again.",
				HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method handles only BadTokenException
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BadTokenException.class)
	public ResponseEntity<CustomException> handleBadTokenException(HttpServletRequest request, BadTokenException ex) {
		return new ResponseEntity<>(new CustomException("Token incorrect. Please try again.", HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}
}
