package com.lms.integration.service.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.reservation.ReservationRepository;
import com.lms.service.reservation.ReservationService;
import com.lms.model.Reservation;
import com.lms.model.enums.ReservationStatus;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationServiceIntegrationTest {

	@Autowired
	private ReservationRepository repository;

	@Autowired
	private ReservationService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Reservation entity1 = new Reservation();
		entity1.setReservationDate(LocalDate.of(2020, 1, 1));
		entity1.setStatus(ReservationStatus.PENDING);
		entity1.setId(1L);

		Reservation entity2 = new Reservation();
		entity2.setReservationDate(LocalDate.of(2020, 2, 1));
		entity2.setStatus(ReservationStatus.FULFILLED);
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Reservation> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Reservation entity3 = new Reservation();
		entity3.setReservationDate(LocalDate.of(2020, 2, 1));
		entity3.setStatus(ReservationStatus.FULFILLED);

		Reservation savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getReservationDate()).isEqualTo(LocalDate.of(2020, 2, 1));
		assertThat(savedEntity.getStatus()).isEqualTo(ReservationStatus.FULFILLED);
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Reservation> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByReservationDate() {
		List<Reservation> entities = service.findByReservationDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getReservationDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByStatus() {
		List<Reservation> entities = service.findByStatus(ReservationStatus.PENDING);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getStatus()).isEqualTo(ReservationStatus.PENDING);
	}

}
