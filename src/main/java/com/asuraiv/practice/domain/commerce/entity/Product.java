package com.asuraiv.practice.domain.commerce.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "product", catalog = "jpa_practice")
@NoArgsConstructor
public class Product {

	@Id
	@Column(name = "product_id")
	private String id;

	private String name;

	@ManyToMany(mappedBy = "products")
	private List<Member> members = new ArrayList<>();

	@Builder
	public Product(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
