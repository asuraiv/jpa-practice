package com.asuraiv.practice.domain.basicexample;

import com.asuraiv.practice.domain.basicexample.entity.Member;
import com.asuraiv.practice.domain.basicexample.entity.Order;
import com.asuraiv.practice.domain.basicexample.entity.OrderItem;
import com.asuraiv.practice.domain.basicexample.helper.EntityMaker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class BasicTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void 회원_생성_및_조회_테스트() {

		Member member = EntityMaker.buildMember();

		entityManager.persist(member);

		Member foundOne = entityManager.find(Member.class, member.getId());

		assertThat(foundOne.getName(), is("홍길동"));
	}

	@Test
	@Transactional
	public void 주문_객체_그래프_탐색() {

		Member member = EntityMaker.buildMember();

		entityManager.persist(member);

		Order order = EntityMaker.buildOrder(member);

		entityManager.persist(order);

		Order foundOne = entityManager.find(Order.class, order.getId());

		assertNotNull(foundOne.getMember());
		assertThat(foundOne.getMember().getName(), is("홍길동"));
		assertThat(foundOne.getOrderItems().get(0).getItem().getName(), is("노인과바다"));
	}

	@Test
	@Transactional
	public void JPQL_조회() {

		Member member = EntityMaker.buildMember();

		entityManager.persist(member);

		Order order = EntityMaker.buildOrder(member);

		entityManager.persist(order);

		String jpql = "select o from Order o join o.member m where m.id = :memberId";

		List<Order> foundList = entityManager.createQuery(jpql, Order.class)
			.setParameter("memberId", member.getId())
			.getResultList();

		assertThat(foundList.get(0).getMember().getName(), is("홍길동"));
	}

	@Test
	@Transactional
	public void 연관관계_저장_실패() {

		OrderItem orderItem = EntityMaker.buildOrderItem(null);

		entityManager.persist(orderItem);

		Order order = EntityMaker.buildOrder();

		order.addOrderItem(orderItem);

		entityManager.persist(order);

		List<OrderItem> orderItems = entityManager
			.createQuery("select orderItem from OrderItem orderItem", OrderItem.class)
			.getResultList();

		// order <-> orderItem 양방향 관계에 orderItem이 owner side이다.
		// 그러므로 order에 orderItem을 추가하여 영속화 후, orderItem을 조회하면 order가 null이 되어 반환된다.
		// order는 owner side가 아니기 때문이다.
		assertNull(orderItems.get(0).getOrder());
	}
}
