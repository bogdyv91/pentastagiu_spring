package com.pentalog.repository;

import java.util.List;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import com.pentalog.model.Authentification;

/**
 * Repository that contains queries to the authentification table
 * 
 * @author Vacariuc Bogdan
 *
 */

@Component
public interface AuthentificationRepository extends Repository<Authentification, Long> {

	public Authentification save(Authentification authentification);

	public void delete(Authentification authentification);

	public List<Authentification> findByToken(String token);

}
