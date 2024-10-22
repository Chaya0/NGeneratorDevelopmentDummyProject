package com.lms.integration.repository.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.library.LibraryRepository;
import com.lms.model.Library;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LibraryRepositoryIntegrationTest {

	@Autowired
	private LibraryRepository repository;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Library entity1 = new Library();
		entity1.setName("name1");
		entity1.setAddress("address1");
		entity1.setCity("city1");
		entity1.setPhone("phone1");

		Library entity2 = new Library();
		entity2.setName("name2");
		entity2.setAddress("address2");
		entity2.setCity("city2");
		entity2.setPhone("phone2");

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Library> entity = repository.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Library entity3 = new Library();
		entity3.setName("name3");
		entity3.setAddress("address3");
		entity3.setCity("city3");
		entity3.setPhone("phone3");

		Library savedEntity = repository.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getName()).isEqualTo("name3");
		assertThat(savedEntity.getAddress()).isEqualTo("address3");
		assertThat(savedEntity.getCity()).isEqualTo("city3");
		assertThat(savedEntity.getPhone()).isEqualTo("phone3");
	}

	@Test
	void testDeleteById() {
		repository.deleteById(1L);

		Optional<Library> deletedEntity = repository.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByName() {
		List<Library> entities = repository.findByName("name1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getName()).isEqualTo("name1");
	}

	@Test
	void testFindByAddress() {
		List<Library> entities = repository.findByAddress("address1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAddress()).isEqualTo("address1");
	}

	@Test
	void testFindByCity() {
		List<Library> entities = repository.findByCity("city1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getCity()).isEqualTo("city1");
	}

	@Test
	void testFindByPhone() {
		List<Library> entities = repository.findByPhone("phone1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPhone()).isEqualTo("phone1");
	}

}
