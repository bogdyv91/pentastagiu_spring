package com.pentalog.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pentalog.model.Authentification;
import com.pentalog.model.User;
import com.pentalog.repository.AuthentificationRepository;
import com.pentalog.utilities.TokenGenerator;

/**
 * Service class for operations with Authentification objects
 * 
 * @author Vacariuc Bogdan
 *
 */

@Service
public class AuthentificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationService.class);

	@Value("${deleteTime}")
	Integer time;

	@Autowired
	AuthentificationRepository authentificationRepository;

	/**
	 * Saves and Authentification object in the database Checks if the random
	 * generated token already exist. If so, generates another one and tries again.
	 * 
	 * @param authentification the object that is saved into the database
	 */
	public void save(Authentification authentification) {
		List<Authentification> authentifications = authentificationRepository.findByToken(authentification.getToken());
		while (authentifications.size() != 0) {
			authentification.setToken(TokenGenerator.getRandomToken());
			authentificationRepository.save(authentification);
			authentifications = authentificationRepository.findByToken(authentification.getToken());
		}
		authentificationRepository.save(authentification);
		startSchedueledTaskForTokenDelete(authentification);
	}

	/**
	 * Starts schedueled task to delete the authentification after the configured
	 * time in .yml file
	 * 
	 * @param authentification
	 */
	public void startSchedueledTaskForTokenDelete(Authentification authentification) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.schedule(() -> authentificationRepository.delete(authentification), time, TimeUnit.SECONDS);
	}

	/**
	 * Deletes an authentification by given token
	 * 
	 * @param token the token whose Authentification object is deleted
	 * @return true if found, else false
	 */
	public void delete(String token) {
		List<Authentification> authentifications = authentificationRepository.findByToken(token);
		authentificationRepository.delete(authentifications.get(0));
	}

	/**
	 * Returns an optional of an User by given token
	 * 
	 * @param token
	 * @return Optional of User
	 */
	public Optional<User> getUserByToken(String token) {
		List<Authentification> authentifications = authentificationRepository.findByToken(token);
		if (authentifications.size() == 1) {
			return Optional.ofNullable(authentifications.get(0).getUser());
		}
		LOGGER.error("User not found by token");
		return Optional.empty();
	}
}
