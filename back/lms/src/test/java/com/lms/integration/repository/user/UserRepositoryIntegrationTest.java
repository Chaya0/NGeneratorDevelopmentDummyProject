package com.lms.integration.repository.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.user.UserRepository;
import com.lms.model.User;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserRepositoryIntegrationTest {

	@Autowired
	private UserRepository repository;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		User entity1 = new User();
		entity1.setUsername("username1");
		entity1.setPassword("password1");
		entity1.setEmail("email1");

		User entity2 = new User();
		entity2.setUsername("username2");
		entity2.setPassword("password2");
		entity2.setEmail("email2");

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<User> entity = repository.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		User entity3 = new User();
		entity3.setUsername("username3");
		entity3.setPassword("password3");
		entity3.setEmail("email3");

		User savedEntity = repository.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getUsername()).isEqualTo("username3");
		assertThat(savedEntity.getPassword()).isEqualTo("password3");
		assertThat(savedEntity.getEmail()).isEqualTo("email3");
	}

	@Test
	void testDeleteById() {
		repository.deleteById(1L);

		Optional<User> deletedEntity = repository.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByUsername() {
		Optional<User> entity1 = repository.findByUsername("username1");
		assertThat(entity1).isPresent();
		assertThat(entity1.get().getUsername()).isEqualTo("username1");
	}

	@Test
	void testFindByPassword() {
		List<User> entities = repository.findByPassword("password1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPassword()).isEqualTo("password1");
	}

	@Test
	void testFindByEmail() {
		Optional<User> entity1 = repository.findByEmail("email1");
		assertThat(entity1).isPresent();
		assertThat(entity1.get().getEmail()).isEqualTo("email1");
	}

}
