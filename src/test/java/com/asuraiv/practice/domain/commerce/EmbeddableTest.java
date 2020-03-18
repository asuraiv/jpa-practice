package com.asuraiv.practice.domain.commerce;

import com.asuraiv.practice.domain.commerce.entity.Member;
import com.asuraiv.practice.domain.commerce.entity.embeddable.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@SpringBootTest
public class EmbeddableTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void Embeddable_저장() {

		Date createdAt = new Date();

		Member member = Member.builder()
			.name("홍길동")
			.city("이천시")
			.street("창전로")
			.zipcode("99999")
			.createdAt(createdAt)
			.updatedAt(createdAt)
			.build();

		entityManager.persist(member);

		Member foundOne = entityManager.find(Member.class, 1L);

		Address address = foundOne.getAddress();
		assertNotNull(address);
		assertThat(address.getCity(), is("이천시"));
	}

	@BeforeEach
	void resetAutoIncrementValue() {
		entityManager.createNativeQuery("ALTER TABLE `member` AUTO_INCREMENT = 1").executeUpdate();
	}
}
