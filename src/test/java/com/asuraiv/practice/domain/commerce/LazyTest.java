package com.asuraiv.practice.domain.commerce;

import com.asuraiv.practice.domain.commerce.entity.Member;
import com.asuraiv.practice.domain.commerce.entity.Order;
import com.asuraiv.practice.domain.commerce.helper.EntityMaker;
import org.hibernate.LazyInitializationException;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class LazyTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

	@Test
	@Transactional
	public void 프록시_로딩_테스트() {

		Member member = EntityMaker.buildMember("홍길동");

		entityManager.persist(member);

		entityManager.clear();

		Member proxy = entityManager.getReference(Member.class, member.getId());

		assertTrue(proxy instanceof HibernateProxy);
	}

	@Test
	@Transactional
	public void 프록시_초기화_예외() {

		Assertions.assertThrows(LazyInitializationException.class, () -> {

			EntityManagerFactory entityManagerFactory = entityManagerFactoryBean.getNativeEntityManagerFactory();
			EntityManager localEntityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = localEntityManager.getTransaction();

			transaction.begin();

			Member member = EntityMaker.buildMember("홍길동");

			localEntityManager.persist(member);

			transaction.commit();

			localEntityManager.clear();

			Member proxy = localEntityManager.getReference(Member.class, member.getId());

			localEntityManager.close();

			proxy.getName(); // 영속성 컨텍스트는 이미 종료되었으므로, 프록시 초기화 예외 발생
		});
	}

	@Test
	@Transactional
	public void 고아_엔티티_제거() {

		Member member = EntityMaker.buildMember("홍길동");

		Order order = EntityMaker.buildOnlyOrder(member);

		entityManager.persist(order);

		entityManager.persist(member);

		member.getOrders().remove(0);

		entityManager.flush();

		Order foundOne = entityManager.find(Order.class, order.getId());

		assertNull(foundOne);
	}
}
