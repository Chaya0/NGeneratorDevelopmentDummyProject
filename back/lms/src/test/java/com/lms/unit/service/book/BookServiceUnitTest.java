package com.lms.unit.service.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.*;
import java.time.*;
import com.lms.exceptions.LmsException;
import com.lms.repository.book.BookRepository;
import com.lms.service.book.BookService;
import com.lms.model.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class BookServiceUnitTest {

	@Mock
	private BookRepository repository;

	@InjectMocks
	private BookService service;

	private Book entity1;
	private Book entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new Book();
		entity1.setTitle("title1");
		entity1.setIsbn("isbn1");
		entity1.setPublishedDate(LocalDate.of(2020, 1, 1));
		entity1.setLanguage("language1");
		entity1.setAvailableCopies(1);
		entity1.setTotalCopies(1);
		entity1.setId(1L);

		entity2 = new Book();
		entity2.setTitle("title2");
		entity2.setIsbn("isbn2");
		entity2.setPublishedDate(LocalDate.of(2020, 2, 1));
		entity2.setLanguage("language2");
		entity2.setAvailableCopies(2);
		entity2.setTotalCopies(2);
		entity2.setId(2L);

	}
	@Test
	void testFindByTitle() {
	when(repository.findByTitle("title1")).thenReturn(List.of(entity1));
		List<Book> entities = service.findByTitle("title1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getTitle()).isEqualTo("title1");
	verify(repository, times(1)).findByTitle("title1");
	}

	@Test
	void testFindByIsbn() {
	when(repository.findByIsbn("isbn1")).thenReturn(List.of(entity1));
		List<Book> entities = service.findByIsbn("isbn1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getIsbn()).isEqualTo("isbn1");
	verify(repository, times(1)).findByIsbn("isbn1");
	}

	@Test
	void testFindByPublishedDate() {
	when(repository.findByPublishedDate(LocalDate.of(2020, 1, 1))).thenReturn(List.of(entity1));
		List<Book> entities = service.findByPublishedDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPublishedDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	verify(repository, times(1)).findByPublishedDate(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByLanguage() {
	when(repository.findByLanguage("language1")).thenReturn(List.of(entity1));
		List<Book> entities = service.findByLanguage("language1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getLanguage()).isEqualTo("language1");
	verify(repository, times(1)).findByLanguage("language1");
	}

	@Test
	void testFindByAvailableCopies() {
	when(repository.findByAvailableCopies(1)).thenReturn(List.of(entity1));
		List<Book> entities = service.findByAvailableCopies(1);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAvailableCopies()).isEqualTo(1);
	verify(repository, times(1)).findByAvailableCopies(1);
	}

	@Test
	void testFindByTotalCopies() {
	when(repository.findByTotalCopies(1)).thenReturn(List.of(entity1));
		List<Book> entities = service.findByTotalCopies(1);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getTotalCopies()).isEqualTo(1);
	verify(repository, times(1)).findByTotalCopies(1);
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<Book> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Book> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<Book> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<Book> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<Book> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<Book> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Book> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<Book> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<Book> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(Book.class))).thenReturn(entity1);
		Book entity3 = new Book();
		entity3.setTitle("title3");
		entity3.setIsbn("isbn3");
		entity3.setPublishedDate(LocalDate.of(2020, 2, 1));
		entity3.setLanguage("language3");
		entity3.setAvailableCopies(3);
		entity3.setTotalCopies(3);

		Book savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(Book.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		Book entity4 = new Book();
		entity4.setTitle("title4");
		entity4.setIsbn("isbn4");
		entity4.setPublishedDate(LocalDate.of(2020, 2, 1));
		entity4.setLanguage("language4");
		entity4.setAvailableCopies(4);
		entity4.setTotalCopies(4);
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(Book.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(Book.class))).thenReturn(entity1);

		entity1.setTitle("title4");
		Book updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getTitle()).isEqualTo("title4");
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(Book.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(Book.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
