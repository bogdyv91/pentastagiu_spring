package com.pentalog.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.pentalog.model.Person;
import com.pentalog.model.User;

public interface PersonRepository extends Repository<Person, Long> {

	public Optional<Person> findPersonByUser(User user);
}
