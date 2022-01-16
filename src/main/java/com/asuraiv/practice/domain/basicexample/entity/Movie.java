package com.asuraiv.practice.domain.basicexample.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "movie", schema = "jpa_practice")
@DiscriminatorValue("M")
public class Movie extends Item {

	private String director;

	private String actor;

	@Builder
	public Movie(String name, int price, int stockQuantity, String director, String actor) {

		super(name, price, stockQuantity);
		this.director = director;
		this.actor = actor;
	}
}
