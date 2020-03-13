package com.asuraiv.practice.domain.commerce;

import com.asuraiv.practice.domain.commerce.entity.Member;
import com.asuraiv.practice.domain.commerce.helper.EntityMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class ManyToManyTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void 다대다_저장_및_조회_테스트() {

		Member member1 = EntityMaker.buildMember();
		member1.addProduct(EntityMaker.buildProduct());

		entityManager.persist(member1);

		Member member = entityManager.find(Member.class, 1L);

		assertThat(member.getProducts().get(0).getName(), is("KF마스크"));
	}

	@BeforeEach
	void resetAutoIncrementValue() {

		entityManager.createNativeQuery("ALTER TABLE `member` AUTO_INCREMENT = 1").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE `product` AUTO_INCREMENT = 1").executeUpdate();
	}
}
