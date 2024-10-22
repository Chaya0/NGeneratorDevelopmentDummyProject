package com.lms.unit.service.fine;

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
import com.lms.repository.fine.FineRepository;
import com.lms.service.fine.FineService;
import com.lms.model.Fine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class FineServiceUnitTest {

	@Mock
	private FineRepository repository;

	@InjectMocks
	private FineService service;

	private Fine entity1;
	private Fine entity2;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		entity1 = new Fine();
		entity1.setFineAmount(1.00);
		entity1.setPaid(true);
		entity1.setFineDate(LocalDate.of(2020, 1, 1));
		entity1.setId(1L);

		entity2 = new Fine();
		entity2.setFineAmount(2.00);
		entity2.setPaid(true);
		entity2.setFineDate(LocalDate.of(2020, 2, 1));
		entity2.setId(2L);

	}
	@Test
	void testFindByFineAmount() {
	when(repository.findByFineAmount(1.00)).thenReturn(List.of(entity1));
		List<Fine> entities = service.findByFineAmount(1.00);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFineAmount()).isEqualTo(1.00);
	verify(repository, times(1)).findByFineAmount(1.00);
	}

	@Test
	void testFindByPaid() {
	when(repository.findByPaid(true)).thenReturn(List.of(entity1));
		List<Fine> entities = service.findByPaid(true);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPaid()).isEqualTo(true);
	verify(repository, times(1)).findByPaid(true);
	}

	@Test
	void testFindByFineDate() {
	when(repository.findByFineDate(LocalDate.of(2020, 1, 1))).thenReturn(List.of(entity1));
		List<Fine> entities = service.findByFineDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFineDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	verify(repository, times(1)).findByFineDate(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindAllWithoutArguments() {
		when(repository.findAll()).thenReturn(List.of(entity1, entity2));
		List<Fine> entities = service.findAll();

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll();
	}

	@Test
	void testFindAllWithPageable() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Fine> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);
		when(repository.findAll(pageable)).thenReturn(page);

		Page<Fine> entities = service.findAll(pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(pageable);
	}

	@Test
	void testFindAllWithSpecification() {
		Specification<Fine> specification = mock(Specification.class);
		when(repository.findAll(specification)).thenReturn(List.of(entity1, entity2));

		List<Fine> entities = service.findAll(specification);

		assertThat(entities).hasSize(2);
		assertThat(entities).containsExactly(entity1, entity2);

		verify(repository, times(1)).findAll(specification);
	}

	@Test
	void testFindAllWithSpecificationAndPageable() {
		Specification<Fine> specification = mock(Specification.class);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Fine> page = new PageImpl<>(List.of(entity1, entity2), pageable, 2);

		when(repository.findAll(specification, pageable)).thenReturn(page);

		Page<Fine> entities = service.findAll(specification, pageable);

		assertThat(entities.getContent()).hasSize(2);
		assertThat(entities.getContent()).containsExactly(entity1, entity2);
		assertThat(entities.getTotalElements()).isEqualTo(2);

		verify(repository, times(1)).findAll(specification, pageable);
	}


	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(entity1));
		Optional<Fine> entity = service.findById(1L);
		assertThat(entity).isPresent();
		assertThat(entity.get()).isEqualTo(entity1);

		verify(repository, times(1)).findById(1L);
	}

	@Test
	void testInsert() throws LmsException {
		when(repository.save(any(Fine.class))).thenReturn(entity1);
		Fine entity3 = new Fine();
		entity3.setFineAmount(3.00);
		entity3.setPaid(true);
		entity3.setFineDate(LocalDate.of(2020, 2, 1));

		Fine savedEntity = service.insert(entity3);

		assertThat(savedEntity).isEqualTo(entity1);
		verify(repository, times(1)).save(any(Fine.class));
	}

	@Test
	void testInsertWithIdThrowsException() {
		Fine entity4 = new Fine();
		entity4.setFineAmount(4.00);
		entity4.setPaid(true);
		entity4.setFineDate(LocalDate.of(2020, 2, 1));
		entity4.setId(99L);

		assertThrows(LmsException.class, () -> service.insert(entity4));

		verify(repository, never()).save(any(Fine.class));
	}

	@Test
	void testUpdate() throws LmsException {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		when(repository.save(any(Fine.class))).thenReturn(entity1);

		entity1.setFineAmount(4.00);
		Fine updatedEntity = service.update(entity1);

		assertThat(updatedEntity.getFineAmount()).isEqualTo(4.00);
		verify(repository, times(1)).findById(entity1.getId());
		verify(repository, times(1)).save(any(Fine.class));
	}

	@Test
	void testUpdateThrowsEntityNotFoundException() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> service.update(entity1));

		verify(repository, never()).save(any(Fine.class));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(entity1.getId())).thenReturn(Optional.of(entity1));
		doNothing().when(repository).deleteById(entity1.getId());

		service.deleteById(entity1.getId());
		verify(repository, times(1)).deleteById(entity1.getId());
	}

}
