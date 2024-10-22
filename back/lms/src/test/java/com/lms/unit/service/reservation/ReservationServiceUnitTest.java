package com.lms.unit.service.reservation;

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
import com.lms.repository.reservation.ReservationRepository;
import com.lms.service.reservation.ReservationService;
import com.lms.model.Reservation;
import com.lms.model.enums.ReservationStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ReservationServiceUnitTest {

	@Mock
	private ReservationRepository repository;

	@InjectMocks
	private ReservationService service;

	private Reservation entity1;
	private Reservation entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new Reservation();
		entity1.setReservationDate(LocalDate.of(2020, 1, 1));
		entity1.setStatus(ReservationStatus.PENDING);
		entity1.setId(1L);

		entity2 = new Reservation();
		entity2.setReservationDate(LocalDate.of(2020, 2, 1));
		entity2.setStatus(ReservationStatus.FULFILLED);
		entity2.setId(2L);

	}
	@Test
	void testFindByReservationDate() {
	when(repository.findByReservationDate(LocalDate.of(2020, 1, 1))).thenReturn(List.of(entity1));
		List<Reservation> entities = service.findByReservationDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getReservationDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	verify(repository, times(1)).findByReservationDate(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByStatus() {
	when(repository.findByStatus(ReservationStatus.PENDING)).thenReturn(List.of(entity1));
		List<Reservation> entities = service.findByStatus(ReservationStatus.PENDING);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getStatus()).isEqualTo(ReservationStatus.PENDING);
	verify(repository, times(1)).findByStatus(ReservationStatus.PENDING);
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<Reservation> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Reservation> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<Reservation> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<Reservation> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<Reservation> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<Reservation> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Reservation> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<Reservation> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<Reservation> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(Reservation.class))).thenReturn(entity1);
		Reservation entity3 = new Reservation();
		entity3.setReservationDate(LocalDate.of(2020, 2, 1));
		entity3.setStatus(ReservationStatus.FULFILLED);

		Reservation savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(Reservation.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		Reservation entity4 = new Reservation();
		entity4.setReservationDate(LocalDate.of(2020, 2, 1));
		entity4.setStatus(ReservationStatus.FULFILLED);
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(Reservation.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(Reservation.class))).thenReturn(entity1);

		entity1.setReservationDate(LocalDate.of(2020, 2, 1));
		Reservation updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getReservationDate()).isEqualTo(LocalDate.of(2020, 2, 1));
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(Reservation.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(Reservation.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
