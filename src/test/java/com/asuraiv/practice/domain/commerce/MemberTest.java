package com.asuraiv.practice.domain.commerce;

import com.asuraiv.practice.domain.commerce.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@DataJpaTest
public class MemberTest {

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
}
