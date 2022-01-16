package com.asuraiv.practice.domain.basicexample;

import com.asuraiv.practice.domain.basicexample.entity.Member;
import com.asuraiv.practice.domain.basicexample.entity.Order;
import com.asuraiv.practice.domain.basicexample.helper.EntityMaker;
import org.hibernate.LazyInitializationException;
import org.hibernate.proxy.HibernateProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.annotation.Rollback;
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

	/*
		아래 테스트는 공유 트랜잭션 매니저가 아닌 개별 트랜잭션 매니저로 테스트 해야함.
		또한 예외 발생 테스트로서, 롤백이 어려우므로,  assertion 후에 공용 entityManager로 delete query를 이용해
		tear down을 해야하므로 rollback = false로 설정함.
	 */
	@Test
	@Rollback(false)
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

			localEntityManager.clear(); // 영속성 컨텍스트를 비워서 proxy가 반환되게 한다.

			// 영속성 컨텍스트에 찾는 엔티티 객체가 있으면 getReference 메서드 호출이라도 proxy가 아닌 실제 엔티티를 반환한
			Member proxy = localEntityManager.getReference(Member.class, member.getId());

			localEntityManager.close(); // 영속성 컨텍스트 종료

			proxy.getName(); // 영속성 컨텍스트는 이미 종료되었으므로, 프록시 초기화 예외 발생
		});

		entityManager.createNativeQuery("DELETE FROM `member` WHERE member_id > 0")
			.executeUpdate();
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
