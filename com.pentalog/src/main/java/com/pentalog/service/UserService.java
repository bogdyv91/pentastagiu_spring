package com.pentalog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pentalog.model.User;
import com.pentalog.repository.UserRepository;

/**
 * Service class for operations with User objects
 * 
 * @author Vacariuc Bogdan
 *
 */
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	/**
	 * Returns an Optional of User if the username with the specified username and
	 * password is found, and an empty Optional if the User was not found.
	 * 
	 * @param username the username that is searched for in the query
	 * @param password the password that is searched for in the query
	 * @return Optional of User
	 */
	public Optional<User> getByUsernameAndPassword(String username, String password) {
		List<User> users = userRepository.findByUsernameAndPassword(username, password);
		if (users.size() == 1) {
			return Optional.of(users.get(0));
		}
		return Optional.empty();
	}
}
