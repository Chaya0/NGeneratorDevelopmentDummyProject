package com.lms.unit.service.role;

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
import com.lms.repository.role.RoleRepository;
import com.lms.service.role.RoleService;
import com.lms.model.Role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class RoleServiceUnitTest {

	@Mock
	private RoleRepository repository;

	@InjectMocks
	private RoleService service;

	private Role entity1;
	private Role entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new Role();
		entity1.setName("name1");
		entity1.setDescription("description1");
		entity1.setId(1L);

		entity2 = new Role();
		entity2.setName("name2");
		entity2.setDescription("description2");
		entity2.setId(2L);

	}
	@Test
	void testFindByName() {
	when(repository.findByName("name1")).thenReturn(Optional.of(entity1));
		Optional<Role> foundEntity = Optional.of(service.findByName("name1"));
		assertThat(foundEntity).isPresent();
		assertThat(foundEntity.get()).isEqualTo(entity1);
	verify(repository, times(1)).findByName("name1");
	}

	@Test
	void testFindByDescription() {
	when(repository.findByDescription("description1")).thenReturn(List.of(entity1));
		List<Role> entities = service.findByDescription("description1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getDescription()).isEqualTo("description1");
	verify(repository, times(1)).findByDescription("description1");
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<Role> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Role> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<Role> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<Role> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<Role> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<Role> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Role> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<Role> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<Role> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(Role.class))).thenReturn(entity1);
		Role entity3 = new Role();
		entity3.setName("name3");
		entity3.setDescription("description3");

		Role savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(Role.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		Role entity4 = new Role();
		entity4.setName("name4");
		entity4.setDescription("description4");
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(Role.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(Role.class))).thenReturn(entity1);

		entity1.setName("name4");
		Role updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getName()).isEqualTo("name4");
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(Role.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(Role.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
