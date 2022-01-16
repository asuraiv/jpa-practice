package com.asuraiv.practice.domain.querypractice;

import com.asuraiv.practice.domain.querypractice.entity.Person;
import com.asuraiv.practice.domain.querypractice.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryTest {

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void testFindByName() {

		final Person person = personRepository.findByName("hong");

		assertNotNull(person);
		assertThat(person.getAge(), is(38));
	}
}
