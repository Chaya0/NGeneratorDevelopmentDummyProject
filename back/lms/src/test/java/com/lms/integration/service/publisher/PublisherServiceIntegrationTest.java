package com.lms.integration.service.publisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.publisher.PublisherRepository;
import com.lms.service.publisher.PublisherService;
import com.lms.model.Publisher;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PublisherServiceIntegrationTest {

	@Autowired
	private PublisherRepository repository;

	@Autowired
	private PublisherService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Publisher entity1 = new Publisher();
		entity1.setName("name1");
		entity1.setAddress("address1");
		entity1.setWebsite("website1");
		entity1.setId(1L);

		Publisher entity2 = new Publisher();
		entity2.setName("name2");
		entity2.setAddress("address2");
		entity2.setWebsite("website2");
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Publisher> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Publisher entity3 = new Publisher();
		entity3.setName("name3");
		entity3.setAddress("address3");
		entity3.setWebsite("website3");

		Publisher savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("name3");
		assertThat(savedEntity.getAddress()).isEqualTo("address3");
		assertThat(savedEntity.getWebsite()).isEqualTo("website3");
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Publisher> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByName() {
		List<Publisher> entities = service.findByName("name1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getName()).isEqualTo("name1");
	}

	@Test
	void testFindByAddress() {
		List<Publisher> entities = service.findByAddress("address1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAddress()).isEqualTo("address1");
	}

	@Test
	void testFindByWebsite() {
		List<Publisher> entities = service.findByWebsite("website1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getWebsite()).isEqualTo("website1");
	}

}
