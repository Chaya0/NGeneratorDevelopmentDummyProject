package com.lms.integration.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.user.UserRepository;
import com.lms.service.user.UserService;
import com.lms.model.User;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceIntegrationTest {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		User entity1 = new User();
		entity1.setUsername("username1");
		entity1.setPassword("password1");
		entity1.setEmail("email1");
		entity1.setId(1L);

		User entity2 = new User();
		entity2.setUsername("username2");
		entity2.setPassword("password2");
		entity2.setEmail("email2");
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<User> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		User entity3 = new User();
		entity3.setUsername("username3");
		entity3.setPassword("password3");
		entity3.setEmail("email3");

		User savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getUsername()).isEqualTo("username3");
		assertThat(savedEntity.getPassword()).isEqualTo("password3");
		assertThat(savedEntity.getEmail()).isEqualTo("email3");
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<User> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByUsername() {
		Optional<User> entity1 = Optional.of(service.findByUsername("username1"));
		assertThat(entity1).isPresent();
		assertThat(entity1.get().getUsername()).isEqualTo("username1");
	}

	@Test
	void testFindByPassword() {
		List<User> entities = service.findByPassword("password1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPassword()).isEqualTo("password1");
	}

	@Test
	void testFindByEmail() {
		Optional<User> entity1 = Optional.of(service.findByEmail("email1"));
		assertThat(entity1).isPresent();
		assertThat(entity1.get().getEmail()).isEqualTo("email1");
	}

}
