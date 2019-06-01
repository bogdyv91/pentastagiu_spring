package com.pentalog.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.pentalog.model.User;

/**
 * Repository that contains queries to the user table
 * 
 * @author Vacariuc Bogdan
 *
 */

@Component
public interface UserRepository extends Repository<User, Long> {

	List<User> findByUsernameAndPassword(String username, String password);
}
