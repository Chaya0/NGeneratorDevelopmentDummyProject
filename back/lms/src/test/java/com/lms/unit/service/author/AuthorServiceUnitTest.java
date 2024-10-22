package com.lms.unit.service.author;

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
import com.lms.repository.author.AuthorRepository;
import com.lms.service.author.AuthorService;
import com.lms.model.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AuthorServiceUnitTest {

	@Mock
	private AuthorRepository repository;

	@InjectMocks
	private AuthorService service;

	private Author entity1;
	private Author entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new Author();
		entity1.setFirstName("firstName1");
		entity1.setLastName("lastName1");
		entity1.setBirthDate(LocalDate.of(2020, 1, 1));
		entity1.setId(1L);

		entity2 = new Author();
		entity2.setFirstName("firstName2");
		entity2.setLastName("lastName2");
		entity2.setBirthDate(LocalDate.of(2020, 2, 1));
		entity2.setId(2L);

	}
	@Test
	void testFindByFirstName() {
	when(repository.findByFirstName("firstName1")).thenReturn(List.of(entity1));
		List<Author> entities = service.findByFirstName("firstName1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFirstName()).isEqualTo("firstName1");
	verify(repository, times(1)).findByFirstName("firstName1");
	}

	@Test
	void testFindByLastName() {
	when(repository.findByLastName("lastName1")).thenReturn(List.of(entity1));
		List<Author> entities = service.findByLastName("lastName1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getLastName()).isEqualTo("lastName1");
	verify(repository, times(1)).findByLastName("lastName1");
	}

	@Test
	void testFindByBirthDate() {
	when(repository.findByBirthDate(LocalDate.of(2020, 1, 1))).thenReturn(List.of(entity1));
		List<Author> entities = service.findByBirthDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getBirthDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	verify(repository, times(1)).findByBirthDate(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<Author> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Author> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<Author> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<Author> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<Author> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<Author> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Author> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<Author> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<Author> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(Author.class))).thenReturn(entity1);
		Author entity3 = new Author();
		entity3.setFirstName("firstName3");
		entity3.setLastName("lastName3");
		entity3.setBirthDate(LocalDate.of(2020, 2, 1));

		Author savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(Author.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		Author entity4 = new Author();
		entity4.setFirstName("firstName4");
		entity4.setLastName("lastName4");
		entity4.setBirthDate(LocalDate.of(2020, 2, 1));
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(Author.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(Author.class))).thenReturn(entity1);

		entity1.setFirstName("firstName4");
		Author updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getFirstName()).isEqualTo("firstName4");
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(Author.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(Author.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
