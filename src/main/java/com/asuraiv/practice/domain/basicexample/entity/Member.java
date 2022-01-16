package com.asuraiv.practice.domain.basicexample.entity;

import com.asuraiv.practice.domain.basicexample.entity.embeddable.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "member", catalog = "jpa_practice")
@NoArgsConstructor
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String name;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();

	@ManyToMany(cascade = {CascadeType.PERSIST})
	@JoinTable(name = "member_product",
		joinColumns = @JoinColumn(name = "member_id"),
		inverseJoinColumns = @JoinColumn(name = "product_id")
	)
	private List<Product> products = new ArrayList<>();

	@Builder
	public Member(Date createdAt, Date updatedAt, String name, String city, String street, String zipcode) {

		super(createdAt, updatedAt);
		this.name = name;
		this.address = Address.builder()
			.city(city)
			.street(street)
			.zipcode(zipcode)
			.build();
	}

	public void addProduct(Product product) {

		products.add(product);

		if(!product.getMembers().contains(this)) {
			product.getMembers().add(this);
		}
	}
	public void addOrder(Order order) {

		orders.add(order);

		order.setMember(this);
	}
}
