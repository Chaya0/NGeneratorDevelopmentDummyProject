package com.lms.integration.repository.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.role.RoleRepository;
import com.lms.model.Role;

import com.lms.repository.user.UserRepository;
import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RoleRepositoryIntegrationTest {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
	userRepository.deleteAll();
	repository.deleteAll();
		Role entity1 = new Role();
		entity1.setName("name1");
		entity1.setDescription("description1");

		Role entity2 = new Role();
		entity2.setName("name2");
		entity2.setDescription("description2");

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Role> entity = repository.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Role entity3 = new Role();
		entity3.setName("name3");
		entity3.setDescription("description3");

		Role savedEntity = repository.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("name3");
		assertThat(savedEntity.getDescription()).isEqualTo("description3");
	}

	@Test
	void testDeleteById() {
		repository.deleteById(1L);

		Optional<Role> deletedEntity = repository.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByName() {
		Optional<Role> entity1 = repository.findByName("name1");
		assertThat(entity1).isPresent();
		assertThat(entity1.get().getName()).isEqualTo("name1");
	}

	@Test
	void testFindByDescription() {
		List<Role> entities = repository.findByDescription("description1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getDescription()).isEqualTo("description1");
	}

}
