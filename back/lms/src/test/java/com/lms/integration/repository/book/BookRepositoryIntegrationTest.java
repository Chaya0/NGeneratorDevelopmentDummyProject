package com.lms.integration.repository.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.book.BookRepository;
import com.lms.model.Book;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookRepositoryIntegrationTest {

	@Autowired
	private BookRepository repository;

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

		Book entity2 = new Book();
		entity2.setTitle("title2");
		entity2.setIsbn("isbn2");
		entity2.setPublishedDate(LocalDate.of(2020, 2, 1));
		entity2.setLanguage("language2");
		entity2.setAvailableCopies(2);
		entity2.setTotalCopies(2);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Book> entity = repository.findById(repository.findAll().get(0).getId());
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

		Book savedEntity = repository.save(entity3);

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
		repository.deleteById(1L);

		Optional<Book> deletedEntity = repository.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByTitle() {
		List<Book> entities = repository.findByTitle("title1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getTitle()).isEqualTo("title1");
	}

	@Test
	void testFindByIsbn() {
		List<Book> entities = repository.findByIsbn("isbn1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getIsbn()).isEqualTo("isbn1");
	}

	@Test
	void testFindByPublishedDate() {
		List<Book> entities = repository.findByPublishedDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPublishedDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByLanguage() {
		List<Book> entities = repository.findByLanguage("language1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getLanguage()).isEqualTo("language1");
	}

	@Test
	void testFindByAvailableCopies() {
		List<Book> entities = repository.findByAvailableCopies(1);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAvailableCopies()).isEqualTo(1);
	}

	@Test
	void testFindByTotalCopies() {
		List<Book> entities = repository.findByTotalCopies(1);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getTotalCopies()).isEqualTo(1);
	}

}
