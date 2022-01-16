package com.asuraiv.practice.domain.querypractice.repository;

import com.asuraiv.practice.domain.querypractice.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

	Person findByName(String name);
}
