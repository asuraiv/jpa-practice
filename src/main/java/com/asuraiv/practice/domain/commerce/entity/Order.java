package com.asuraiv.practice.domain.commerce.entity;

import com.asuraiv.practice.domain.commerce.constant.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "order", catalog = "jpa_practice")
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id")
	private Member member;

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderedAt;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Builder
	public Order(Member member, Date orderedAt, OrderStatus status) {
		this.orderedAt = orderedAt;
		this.status = status;
		this.setMember(member);
	}

	private void setMember(Member member) {

		if(this.member != null) {
			this.member.getOrders().remove(this);
		}

		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}
}
