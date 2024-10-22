package com.lms.unit.service.library;

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
import com.lms.repository.library.LibraryRepository;
import com.lms.service.library.LibraryService;
import com.lms.model.Library;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class LibraryServiceUnitTest {

	@Mock
	private LibraryRepository repository;

	@InjectMocks
	private LibraryService service;

	private Library entity1;
	private Library entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new Library();
		entity1.setName("name1");
		entity1.setAddress("address1");
		entity1.setCity("city1");
		entity1.setPhone("phone1");
		entity1.setId(1L);

		entity2 = new Library();
		entity2.setName("name2");
		entity2.setAddress("address2");
		entity2.setCity("city2");
		entity2.setPhone("phone2");
		entity2.setId(2L);

	}
	@Test
	void testFindByName() {
	when(repository.findByName("name1")).thenReturn(List.of(entity1));
		List<Library> entities = service.findByName("name1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getName()).isEqualTo("name1");
	verify(repository, times(1)).findByName("name1");
	}

	@Test
	void testFindByAddress() {
	when(repository.findByAddress("address1")).thenReturn(List.of(entity1));
		List<Library> entities = service.findByAddress("address1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAddress()).isEqualTo("address1");
	verify(repository, times(1)).findByAddress("address1");
	}

	@Test
	void testFindByCity() {
	when(repository.findByCity("city1")).thenReturn(List.of(entity1));
		List<Library> entities = service.findByCity("city1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getCity()).isEqualTo("city1");
	verify(repository, times(1)).findByCity("city1");
	}

	@Test
	void testFindByPhone() {
	when(repository.findByPhone("phone1")).thenReturn(List.of(entity1));
		List<Library> entities = service.findByPhone("phone1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPhone()).isEqualTo("phone1");
	verify(repository, times(1)).findByPhone("phone1");
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<Library> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Library> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<Library> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<Library> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<Library> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<Library> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Library> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<Library> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<Library> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(Library.class))).thenReturn(entity1);
		Library entity3 = new Library();
		entity3.setName("name3");
		entity3.setAddress("address3");
		entity3.setCity("city3");
		entity3.setPhone("phone3");

		Library savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(Library.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		Library entity4 = new Library();
		entity4.setName("name4");
		entity4.setAddress("address4");
		entity4.setCity("city4");
		entity4.setPhone("phone4");
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(Library.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(Library.class))).thenReturn(entity1);

		entity1.setName("name4");
		Library updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getName()).isEqualTo("name4");
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(Library.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(Library.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
