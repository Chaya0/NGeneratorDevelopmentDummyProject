package com.lms.integration.service.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.role.RoleRepository;
import com.lms.repository.user.UserRepository;
import com.lms.service.role.RoleService;
import com.lms.model.Role;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleServiceIntegrationTest {

	@Autowired
	private RoleRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService service;

	@BeforeEach
	void setUp() {
	userRepository.deleteAll();
	repository.deleteAll();
		Role entity1 = new Role();
		entity1.setName("name1");
		entity1.setDescription("description1");
		entity1.setId(1L);

		Role entity2 = new Role();
		entity2.setName("name2");
		entity2.setDescription("description2");
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Role> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Role entity3 = new Role();
		entity3.setName("name3");
		entity3.setDescription("description3");

		Role savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("name3");
		assertThat(savedEntity.getDescription()).isEqualTo("description3");
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Role> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByName() {
		Optional<Role> entity1 = Optional.of(service.findByName("name1"));
		assertThat(entity1).isPresent();
		assertThat(entity1.get().getName()).isEqualTo("name1");
	}

	@Test
	void testFindByDescription() {
		List<Role> entities = service.findByDescription("description1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getDescription()).isEqualTo("description1");
	}

}
