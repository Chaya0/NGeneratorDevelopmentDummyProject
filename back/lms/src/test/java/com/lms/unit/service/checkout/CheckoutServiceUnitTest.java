package com.lms.unit.service.checkout;

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
import com.lms.repository.checkout.CheckoutRepository;
import com.lms.service.checkout.CheckoutService;
import com.lms.model.Checkout;
import com.lms.model.enums.CheckoutStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CheckoutServiceUnitTest {

	@Mock
	private CheckoutRepository repository;

	@InjectMocks
	private CheckoutService service;

	private Checkout entity1;
	private Checkout entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new Checkout();
		entity1.setCheckoutDate(LocalDateTime.of(2020, 1, 1, 0, 0));
		entity1.setDueDate(LocalDate.of(2020, 1, 1));
		entity1.setReturnDate(LocalDate.of(2020, 1, 1));
		entity1.setStatus(CheckoutStatus.BORROWED);
		entity1.setId(1L);

		entity2 = new Checkout();
		entity2.setCheckoutDate(LocalDateTime.of(2020, 2, 1, 0, 0));
		entity2.setDueDate(LocalDate.of(2020, 2, 1));
		entity2.setReturnDate(LocalDate.of(2020, 2, 1));
		entity2.setStatus(CheckoutStatus.RETURNED);
		entity2.setId(2L);

	}
	@Test
	void testFindByCheckoutDate() {
	when(repository.findByCheckoutDate(LocalDateTime.of(2020, 1, 1, 0, 0))).thenReturn(List.of(entity1));
		List<Checkout> entities = service.findByCheckoutDate(LocalDateTime.of(2020, 1, 1, 0, 0));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getCheckoutDate()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0));
	verify(repository, times(1)).findByCheckoutDate(LocalDateTime.of(2020, 1, 1, 0, 0));
	}

	@Test
	void testFindByDueDate() {
	when(repository.findByDueDate(LocalDate.of(2020, 1, 1))).thenReturn(List.of(entity1));
		List<Checkout> entities = service.findByDueDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getDueDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	verify(repository, times(1)).findByDueDate(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByReturnDate() {
	when(repository.findByReturnDate(LocalDate.of(2020, 1, 1))).thenReturn(List.of(entity1));
		List<Checkout> entities = service.findByReturnDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getReturnDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	verify(repository, times(1)).findByReturnDate(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByStatus() {
	when(repository.findByStatus(CheckoutStatus.BORROWED)).thenReturn(List.of(entity1));
		List<Checkout> entities = service.findByStatus(CheckoutStatus.BORROWED);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getStatus()).isEqualTo(CheckoutStatus.BORROWED);
	verify(repository, times(1)).findByStatus(CheckoutStatus.BORROWED);
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<Checkout> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Checkout> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<Checkout> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<Checkout> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<Checkout> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<Checkout> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Checkout> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<Checkout> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<Checkout> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(Checkout.class))).thenReturn(entity1);
		Checkout entity3 = new Checkout();
		entity3.setCheckoutDate(LocalDateTime.of(2020, 2, 1, 0, 0));
		entity3.setDueDate(LocalDate.of(2020, 2, 1));
		entity3.setReturnDate(LocalDate.of(2020, 2, 1));
		entity3.setStatus(CheckoutStatus.RETURNED);

		Checkout savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(Checkout.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		Checkout entity4 = new Checkout();
		entity4.setCheckoutDate(LocalDateTime.of(2020, 2, 1, 0, 0));
		entity4.setDueDate(LocalDate.of(2020, 2, 1));
		entity4.setReturnDate(LocalDate.of(2020, 2, 1));
		entity4.setStatus(CheckoutStatus.RETURNED);
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(Checkout.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(Checkout.class))).thenReturn(entity1);

		entity1.setCheckoutDate(LocalDateTime.of(2020, 2, 1, 0, 0));
		Checkout updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getCheckoutDate()).isEqualTo(LocalDateTime.of(2020, 2, 1, 0, 0));
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(Checkout.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(Checkout.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
