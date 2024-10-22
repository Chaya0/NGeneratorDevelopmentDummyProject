package com.lms.integration.repository.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.author.AuthorRepository;
import com.lms.model.Author;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorRepositoryIntegrationTest {

	@Autowired
	private AuthorRepository repository;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Author entity1 = new Author();
		entity1.setFirstName("firstName1");
		entity1.setLastName("lastName1");
		entity1.setBirthDate(LocalDate.of(2020, 1, 1));

		Author entity2 = new Author();
		entity2.setFirstName("firstName2");
		entity2.setLastName("lastName2");
		entity2.setBirthDate(LocalDate.of(2020, 2, 1));

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Author> entity = repository.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Author entity3 = new Author();
		entity3.setFirstName("firstName3");
		entity3.setLastName("lastName3");
		entity3.setBirthDate(LocalDate.of(2020, 2, 1));

		Author savedEntity = repository.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getFirstName()).isEqualTo("firstName3");
		assertThat(savedEntity.getLastName()).isEqualTo("lastName3");
		assertThat(savedEntity.getBirthDate()).isEqualTo(LocalDate.of(2020, 2, 1));
	}

	@Test
	void testDeleteById() {
		repository.deleteById(1L);

		Optional<Author> deletedEntity = repository.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByFirstName() {
		List<Author> entities = repository.findByFirstName("firstName1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFirstName()).isEqualTo("firstName1");
	}

	@Test
	void testFindByLastName() {
		List<Author> entities = repository.findByLastName("lastName1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getLastName()).isEqualTo("lastName1");
	}

	@Test
	void testFindByBirthDate() {
		List<Author> entities = repository.findByBirthDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getBirthDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

}
