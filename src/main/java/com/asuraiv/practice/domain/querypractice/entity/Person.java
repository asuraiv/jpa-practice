package com.asuraiv.practice.domain.commerce.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "person", schema = "jpa_practice")
public class Person {

	@Id
	private Long id;

	private String name;
	private String address;
	private String phone;
}
