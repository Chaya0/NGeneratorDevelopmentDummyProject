package com.lms.integration.repository.publisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.publisher.PublisherRepository;
import com.lms.model.Publisher;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PublisherRepositoryIntegrationTest {

	@Autowired
	private PublisherRepository repository;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Publisher entity1 = new Publisher();
		entity1.setName("name1");
		entity1.setAddress("address1");
		entity1.setWebsite("website1");

		Publisher entity2 = new Publisher();
		entity2.setName("name2");
		entity2.setAddress("address2");
		entity2.setWebsite("website2");

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Publisher> entity = repository.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Publisher entity3 = new Publisher();
		entity3.setName("name3");
		entity3.setAddress("address3");
		entity3.setWebsite("website3");

		Publisher savedEntity = repository.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("name3");
		assertThat(savedEntity.getAddress()).isEqualTo("address3");
		assertThat(savedEntity.getWebsite()).isEqualTo("website3");
	}

	@Test
	void testDeleteById() {
		repository.deleteById(1L);

		Optional<Publisher> deletedEntity = repository.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByName() {
		List<Publisher> entities = repository.findByName("name1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getName()).isEqualTo("name1");
	}

	@Test
	void testFindByAddress() {
		List<Publisher> entities = repository.findByAddress("address1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAddress()).isEqualTo("address1");
	}

	@Test
	void testFindByWebsite() {
		List<Publisher> entities = repository.findByWebsite("website1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getWebsite()).isEqualTo("website1");
	}

}
