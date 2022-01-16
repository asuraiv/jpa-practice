package com.asuraiv.practice.domain.basicexample.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "order_item", catalog = "jpa_practice")
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	private int orderPrice;

	private int count;

	@Builder
	public OrderItem(Item item, Order order, int orderPrice, int count) {

		this.item = item;
		this.order = order;
		this.orderPrice = orderPrice;
		this.count = count;
	}
}
