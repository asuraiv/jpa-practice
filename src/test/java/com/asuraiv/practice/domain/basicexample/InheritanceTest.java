package com.asuraiv.practice.domain.basicexample;

import com.asuraiv.practice.domain.basicexample.entity.Book;
import com.asuraiv.practice.domain.basicexample.entity.Item;
import com.asuraiv.practice.domain.basicexample.entity.Member;
import com.asuraiv.practice.domain.basicexample.helper.EntityMaker;
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

		Item item = entityManager.find(Item.class, book.getId());

		Book foundOne = (Book) item;

		assertThat(foundOne.getAuthor(), is("해밍웨이"));
	}

	@Test
	@Transactional
	public void MappedSuperclass_저장() {

		Member member = EntityMaker.buildMember();

		entityManager.persist(member);

		Member foundOne = entityManager.find(Member.class, member.getId());

		assertNotNull(foundOne.getCreatedAt());
		assertNotNull(foundOne.getUpdatedAt());
	}
}
