package com.lms.integration.service.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.library.LibraryRepository;
import com.lms.service.library.LibraryService;
import com.lms.model.Library;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LibraryServiceIntegrationTest {

	@Autowired
	private LibraryRepository repository;

	@Autowired
	private LibraryService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Library entity1 = new Library();
		entity1.setName("name1");
		entity1.setAddress("address1");
		entity1.setCity("city1");
		entity1.setPhone("phone1");
		entity1.setId(1L);

		Library entity2 = new Library();
		entity2.setName("name2");
		entity2.setAddress("address2");
		entity2.setCity("city2");
		entity2.setPhone("phone2");
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Library> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Library entity3 = new Library();
		entity3.setName("name3");
		entity3.setAddress("address3");
		entity3.setCity("city3");
		entity3.setPhone("phone3");

		Library savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("name3");
		assertThat(savedEntity.getAddress()).isEqualTo("address3");
		assertThat(savedEntity.getCity()).isEqualTo("city3");
		assertThat(savedEntity.getPhone()).isEqualTo("phone3");
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Library> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByName() {
		List<Library> entities = service.findByName("name1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getName()).isEqualTo("name1");
	}

	@Test
	void testFindByAddress() {
		List<Library> entities = service.findByAddress("address1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAddress()).isEqualTo("address1");
	}

	@Test
	void testFindByCity() {
		List<Library> entities = service.findByCity("city1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getCity()).isEqualTo("city1");
	}

	@Test
	void testFindByPhone() {
		List<Library> entities = service.findByPhone("phone1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPhone()).isEqualTo("phone1");
	}

}
