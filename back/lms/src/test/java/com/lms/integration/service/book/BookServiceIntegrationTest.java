package com.lms.integration.service.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.book.BookRepository;
import com.lms.service.book.BookService;
import com.lms.model.Book;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookServiceIntegrationTest {

	@Autowired
	private BookRepository repository;

	@Autowired
	private BookService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Book entity1 = new Book();
		entity1.setTitle("title1");
		entity1.setIsbn("isbn1");
		entity1.setPublishedDate(LocalDate.of(2020, 1, 1));
		entity1.setLanguage("language1");
		entity1.setAvailableCopies(1);
		entity1.setTotalCopies(1);
		entity1.setId(1L);

		Book entity2 = new Book();
		entity2.setTitle("title2");
		entity2.setIsbn("isbn2");
		entity2.setPublishedDate(LocalDate.of(2020, 2, 1));
		entity2.setLanguage("language2");
		entity2.setAvailableCopies(2);
		entity2.setTotalCopies(2);
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Book> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Book entity3 = new Book();
		entity3.setTitle("title3");
		entity3.setIsbn("isbn3");
		entity3.setPublishedDate(LocalDate.of(2020, 2, 1));
		entity3.setLanguage("language3");
		entity3.setAvailableCopies(3);
		entity3.setTotalCopies(3);

		Book savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getTitle()).isEqualTo("title3");
		assertThat(savedEntity.getIsbn()).isEqualTo("isbn3");
		assertThat(savedEntity.getPublishedDate()).isEqualTo(LocalDate.of(2020, 2, 1));
		assertThat(savedEntity.getLanguage()).isEqualTo("language3");
		assertThat(savedEntity.getAvailableCopies()).isEqualTo(3);
		assertThat(savedEntity.getTotalCopies()).isEqualTo(3);
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Book> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByTitle() {
		List<Book> entities = service.findByTitle("title1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getTitle()).isEqualTo("title1");
	}

	@Test
	void testFindByIsbn() {
		List<Book> entities = service.findByIsbn("isbn1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getIsbn()).isEqualTo("isbn1");
	}

	@Test
	void testFindByPublishedDate() {
		List<Book> entities = service.findByPublishedDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPublishedDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByLanguage() {
		List<Book> entities = service.findByLanguage("language1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getLanguage()).isEqualTo("language1");
	}

	@Test
	void testFindByAvailableCopies() {
		List<Book> entities = service.findByAvailableCopies(1);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAvailableCopies()).isEqualTo(1);
	}

	@Test
	void testFindByTotalCopies() {
		List<Book> entities = service.findByTotalCopies(1);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getTotalCopies()).isEqualTo(1);
	}

}
