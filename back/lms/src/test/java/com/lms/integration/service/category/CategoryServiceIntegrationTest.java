package com.lms.integration.service.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.category.CategoryRepository;
import com.lms.service.category.CategoryService;
import com.lms.model.Category;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryServiceIntegrationTest {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private CategoryService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Category entity1 = new Category();
		entity1.setName("name1");
		entity1.setId(1L);

		Category entity2 = new Category();
		entity2.setName("name2");
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Category> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Category entity3 = new Category();
		entity3.setName("name3");

		Category savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("name3");
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Category> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByName() {
		List<Category> entities = service.findByName("name1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getName()).isEqualTo("name1");
	}

}
