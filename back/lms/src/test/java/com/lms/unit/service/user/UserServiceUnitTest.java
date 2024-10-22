package com.lms.unit.service.user;

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
import com.lms.repository.user.UserRepository;
import com.lms.service.user.UserService;
import com.lms.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class UserServiceUnitTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService service;

	private User entity1;
	private User entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new User();
		entity1.setUsername("username1");
		entity1.setPassword("password1");
		entity1.setEmail("email1");
		entity1.setId(1L);

		entity2 = new User();
		entity2.setUsername("username2");
		entity2.setPassword("password2");
		entity2.setEmail("email2");
		entity2.setId(2L);

	}
	@Test
	void testFindByUsername() {
	when(repository.findByUsername("username1")).thenReturn(Optional.of(entity1));
		Optional<User> foundEntity = Optional.of(service.findByUsername("username1"));
		assertThat(foundEntity).isPresent();
		assertThat(foundEntity.get()).isEqualTo(entity1);
	verify(repository, times(1)).findByUsername("username1");
	}

	@Test
	void testFindByPassword() {
	when(repository.findByPassword("password1")).thenReturn(List.of(entity1));
		List<User> entities = service.findByPassword("password1");
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPassword()).isEqualTo("password1");
	verify(repository, times(1)).findByPassword("password1");
	}

	@Test
	void testFindByEmail() {
	when(repository.findByEmail("email1")).thenReturn(Optional.of(entity1));
		Optional<User> foundEntity = Optional.of(service.findByEmail("email1"));
		assertThat(foundEntity).isPresent();
		assertThat(foundEntity.get()).isEqualTo(entity1);
	verify(repository, times(1)).findByEmail("email1");
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<User> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<User> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<User> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<User> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<User> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<User> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<User> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<User> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<User> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(User.class))).thenReturn(entity1);
		User entity3 = new User();
		entity3.setUsername("username3");
		entity3.setPassword("password3");
		entity3.setEmail("email3");

		User savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(User.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		User entity4 = new User();
		entity4.setUsername("username4");
		entity4.setPassword("password4");
		entity4.setEmail("email4");
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(User.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(User.class))).thenReturn(entity1);

		entity1.setUsername("username4");
		User updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getUsername()).isEqualTo("username4");
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(User.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(User.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
