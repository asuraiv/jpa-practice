package com.asuraiv.practice.domain.commerce.helper;

import com.asuraiv.practice.domain.commerce.constant.OrderStatus;
import com.asuraiv.practice.domain.commerce.entity.Item;
import com.asuraiv.practice.domain.commerce.entity.Member;
import com.asuraiv.practice.domain.commerce.entity.Order;
import com.asuraiv.practice.domain.commerce.entity.OrderItem;
import com.asuraiv.practice.domain.commerce.entity.Product;

import java.util.Date;

public class EntityMaker {

	public static Member buildMember() {
		return buildMember("홍길동");
	}

	public static Member buildMember(String name) {

		return Member.builder()
			.name(name)
			.city("서울시")
			.street("을지로")
			.zipcode("9999")
			.build();
	}

	public static Order buildOnlyOrder(Member member) {

		return Order.builder()
			.orderedAt(new Date())
			.status(OrderStatus.ORDER)
			.member(member)
			.build();
	}

	public static Order buildOrder() {

		return Order.builder()
			.orderedAt(new Date())
			.status(OrderStatus.ORDER)
			.member(buildMember())
			.build();
	}

	public static Order buildOrder(Member member) {

		Order order = Order.builder()
			.member(member)
			.orderedAt(new Date())
			.status(OrderStatus.ORDER)
			.build();

		order.addOrderItem(buildOrderItem(order));

		return order;
	}

	public static OrderItem buildOrderItem(Order order) {

		return OrderItem.builder()
			.item(Item.builder()
				.name("KF마스크")
				.price(1000)
				.stockQuantity(10)
				.build()
			)
			.order(order)
			.count(10)
			.orderPrice(10000)
			.build();
	}

	public static Product buildProduct() {
		return Product.builder()
			.id("product001")
			.name("KF마스크")
			.build();
	}
}
