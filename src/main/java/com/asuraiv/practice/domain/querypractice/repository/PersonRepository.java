package com.asuraiv.practice.domain.commerce.repository;

import com.asuraiv.practice.domain.commerce.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
