package com.lms.unit.service.publisher;

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
import com.lms.repository.publisher.PublisherRepository;
import com.lms.service.publisher.PublisherService;
import com.lms.model.Publisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class PublisherServiceUnitTest {

	@Mock
	private PublisherRepository repository;

	@InjectMocks
	private PublisherService service;

	private Publisher entity1;
	private Publisher entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new Publisher();
		entity1.setName("name1");
		entity1.setAddress("address1");
		entity1.setWebsite("website1");
		entity1.setId(1L);

		entity2 = new Publisher();
		entity2.setName("name2");
		entity2.setAddress("address2");
		entity2.setWebsite("website2");
		entity2.setId(2L);

	}
	@Test
	void testFindByName() {
	when(repository.findByName("name1")).thenReturn(List.of(entity1));
		List<Publisher> entities = service.findByName("name1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getName()).isEqualTo("name1");
	verify(repository, times(1)).findByName("name1");
	}

	@Test
	void testFindByAddress() {
	when(repository.findByAddress("address1")).thenReturn(List.of(entity1));
		List<Publisher> entities = service.findByAddress("address1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getAddress()).isEqualTo("address1");
	verify(repository, times(1)).findByAddress("address1");
	}

	@Test
	void testFindByWebsite() {
	when(repository.findByWebsite("website1")).thenReturn(List.of(entity1));
		List<Publisher> entities = service.findByWebsite("website1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getWebsite()).isEqualTo("website1");
	verify(repository, times(1)).findByWebsite("website1");
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<Publisher> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Publisher> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<Publisher> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<Publisher> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<Publisher> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<Publisher> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Publisher> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<Publisher> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<Publisher> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(Publisher.class))).thenReturn(entity1);
		Publisher entity3 = new Publisher();
		entity3.setName("name3");
		entity3.setAddress("address3");
		entity3.setWebsite("website3");

		Publisher savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(Publisher.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		Publisher entity4 = new Publisher();
		entity4.setName("name4");
		entity4.setAddress("address4");
		entity4.setWebsite("website4");
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(Publisher.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(Publisher.class))).thenReturn(entity1);

		entity1.setName("name4");
		Publisher updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getName()).isEqualTo("name4");
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(Publisher.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(Publisher.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
