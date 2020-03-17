package com.asuraiv.practice.domain.commerce;

import com.asuraiv.practice.domain.commerce.entity.Book;
import com.asuraiv.practice.domain.commerce.entity.Item;
import com.asuraiv.practice.domain.commerce.entity.Member;
import com.asuraiv.practice.domain.commerce.helper.EntityMaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class InheritanceTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void 상속관계_저장() {

		Book book = EntityMaker.buildBook();

		entityManager.persist(book);

		Item item = entityManager.find(Item.class, 1L);

		Book foundOne = (Book) item;

		assertThat(foundOne.getAuthor(), is("해밍웨이"));
	}

	@Test
	@Transactional
	public void MappedSuperclass_저장() {

		Member member = EntityMaker.buildMember();

		entityManager.persist(member);

		Member foundOne = entityManager.find(Member.class, 1L);

		assertNotNull(foundOne.getCreatedAt());
		assertNotNull(foundOne.getUpdatedAt());
	}

	@BeforeEach
	void resetAutoIncrementValue() {

		entityManager.createNativeQuery("ALTER TABLE `item` AUTO_INCREMENT = 1").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE `member` AUTO_INCREMENT = 1").executeUpdate();
	}
}
