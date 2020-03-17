package com.asuraiv.practice.domain.commerce.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@DiscriminatorValue("B")
@Table(name = "book", schema = "jpa_practice")
public class Book extends Item {

	private String author;

	private String isbn;

	@Builder
	public Book(String name, int price, int stockQuantity, String author, String isbn) {
		super(name, price, stockQuantity);
		this.author = author;
		this.isbn = isbn;
	}
}
