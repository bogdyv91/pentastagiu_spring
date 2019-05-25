package com.pentalog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pentalog.converter.UserConverter;
import com.pentalog.dto.UserDTO;
import com.pentalog.model.Authentification;
import com.pentalog.model.User;
import com.pentalog.service.AuthentificationService;
import com.pentalog.service.UserService;
import com.pentalog.utilities.TokenGenerator;

/**
 * Controller clas for services using users
 * 
 * @author Vacariuc Bogdan
 *
 */

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserConverter userConverter;

	@Autowired
	AuthentificationService authentificationService;

	/**
	 * Get http request used to get data data transfer object User In this method a
	 * token is generated for further checks and saved into authentification table
	 * 
	 * @param userDTO data transfer object of User needed to login
	 * @return ACCEPTED status if found, else NOT_FOUND
	 */
	@GetMapping
	public ResponseEntity<?> login(UserDTO userDTO) {
		Optional<User> user = userService.getByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
		if (user.isPresent()) {
			authentificationService.save(new Authentification(TokenGenerator.getRandomToken(), user.get()));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	/**
	 * Delete http request used to delete the token generated in the login
	 * 
	 * @param token generated token for authentification table and further checks
	 * @return ACCEPTED status if token was found in authentification table, else
	 *         BAD_REQUEST
	 * @see login
	 */
	@DeleteMapping
	public ResponseEntity<?> logout(@RequestParam String token) {
		if (authentificationService.delete(token) == true) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

}
