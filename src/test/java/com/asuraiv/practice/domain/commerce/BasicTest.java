package com.asuraiv.practice.domain.commerce;

import com.asuraiv.practice.domain.commerce.constant.OrderStatus;
import com.asuraiv.practice.domain.commerce.entity.Item;
import com.asuraiv.practice.domain.commerce.entity.Member;
import com.asuraiv.practice.domain.commerce.entity.Order;
import com.asuraiv.practice.domain.commerce.entity.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class BasicTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void 회원_생성_및_조회_테스트() {

		Member member = Member.builder()
			.name("홍길동")
			.city("서울시")
			.street("을지로")
			.zipcode("9999")
			.build();

		entityManager.persist(member);

		Member foundOne = entityManager.find(Member.class, member.getId());

		assertThat(foundOne.getName(), is("홍길동"));
	}

	@Test
	@Transactional
	public void 주문_객체_그래프_탐색() {

		Member member = buildMember();

		entityManager.persist(member);

		Order order = buildOrder(member);

		entityManager.persist(order);

		Order foundOne = entityManager.find(Order.class, 1L);

		assertNotNull(foundOne.getMember());
		assertThat(foundOne.getMember().getName(), is("홍길동"));
		assertThat(foundOne.getOrderItems().get(0).getItem().getName(), is("KF마스크"));
	}

	@Test
	@Transactional
	public void JPQL_조회() {

		Member member = buildMember();

		entityManager.persist(member);

		Order order = buildOrder(member);

		entityManager.persist(order);

		String jpql = "select o from Order o join o.member m where m.id = :memberId";

		List<Order> foundList = entityManager.createQuery(jpql, Order.class)
			.setParameter("memberId", 1L)
			.getResultList();

		assertThat(foundList.get(0).getMember().getName(), is("홍길동"));
	}

	@BeforeEach
	void resetAutoIncrementValue() {

		entityManager.createNativeQuery("ALTER TABLE `member` AUTO_INCREMENT = 1").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE `order` AUTO_INCREMENT = 1").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE `order_item` AUTO_INCREMENT = 1").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE `item` AUTO_INCREMENT = 1").executeUpdate();
	}

	private Member buildMember() {

		return Member.builder()
			.name("홍길동")
			.city("서울시")
			.street("을지로")
			.zipcode("9999")
			.build();
	}

	private Order buildOrder(Member member) {
		Order order = Order.builder()
			.member(member)
			.orderedAt(new Date())
			.status(OrderStatus.ORDER)
			.build();

		order.addOrderItem(OrderItem.builder()
			.item(Item.builder()
				.name("KF마스크")
				.price(1000)
				.stockQuantity(10)
				.build()
			)
			.count(10)
			.orderPrice(10000)
			.order(order)
			.build()
		);
		return order;
	}
}
