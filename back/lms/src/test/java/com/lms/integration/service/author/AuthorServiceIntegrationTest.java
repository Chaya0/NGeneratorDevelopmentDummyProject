package com.lms.integration.service.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.author.AuthorRepository;
import com.lms.service.author.AuthorService;
import com.lms.model.Author;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthorServiceIntegrationTest {

	@Autowired
	private AuthorRepository repository;

	@Autowired
	private AuthorService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Author entity1 = new Author();
		entity1.setFirstName("firstName1");
		entity1.setLastName("lastName1");
		entity1.setBirthDate(LocalDate.of(2020, 1, 1));
		entity1.setId(1L);

		Author entity2 = new Author();
		entity2.setFirstName("firstName2");
		entity2.setLastName("lastName2");
		entity2.setBirthDate(LocalDate.of(2020, 2, 1));
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Author> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Author entity3 = new Author();
		entity3.setFirstName("firstName3");
		entity3.setLastName("lastName3");
		entity3.setBirthDate(LocalDate.of(2020, 2, 1));

		Author savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getFirstName()).isEqualTo("firstName3");
		assertThat(savedEntity.getLastName()).isEqualTo("lastName3");
		assertThat(savedEntity.getBirthDate()).isEqualTo(LocalDate.of(2020, 2, 1));
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Author> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByFirstName() {
		List<Author> entities = service.findByFirstName("firstName1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFirstName()).isEqualTo("firstName1");
	}

	@Test
	void testFindByLastName() {
		List<Author> entities = service.findByLastName("lastName1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getLastName()).isEqualTo("lastName1");
	}

	@Test
	void testFindByBirthDate() {
		List<Author> entities = service.findByBirthDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getBirthDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

}
