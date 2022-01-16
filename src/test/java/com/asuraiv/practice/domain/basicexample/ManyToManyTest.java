package com.asuraiv.practice.domain.basicexample;

import com.asuraiv.practice.domain.basicexample.entity.Member;
import com.asuraiv.practice.domain.basicexample.helper.EntityMaker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class ManyToManyTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void 다대다_저장_및_조회_테스트() {

		Member member = EntityMaker.buildMember();
		member.addProduct(EntityMaker.buildProduct());

		entityManager.persist(member);

		Member foundOne = entityManager.find(Member.class, member.getId());

		assertThat(foundOne.getProducts().get(0).getName(), is("KF마스크"));
	}

	@Test
	@Transactional
	public void 다대다_중복_저장_테스트() {

		Member member1 = EntityMaker.buildMember();
		member1.addProduct(EntityMaker.buildProduct());

		entityManager.persist(member1);

		Member member2 = EntityMaker.buildMember("김철수");
		member2.addProduct(member1.getProducts().get(0));

		entityManager.persist(member2);

		entityManager.flush();

		Member foundOne1 = entityManager.find(Member.class, member1.getId());
		Member foundOne2 = entityManager.find(Member.class, member2.getId());

		assertEquals(foundOne1.getProducts().get(0), foundOne2.getProducts().get(0));
	}
}
