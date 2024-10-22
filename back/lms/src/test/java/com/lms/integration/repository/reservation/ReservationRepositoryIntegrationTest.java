package com.lms.integration.repository.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.reservation.ReservationRepository;
import com.lms.model.Reservation;

import com.lms.model.enums.ReservationStatus;
import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationRepositoryIntegrationTest {

	@Autowired
	private ReservationRepository repository;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Reservation entity1 = new Reservation();
		entity1.setReservationDate(LocalDate.of(2020, 1, 1));
		entity1.setStatus(ReservationStatus.PENDING);

		Reservation entity2 = new Reservation();
		entity2.setReservationDate(LocalDate.of(2020, 2, 1));
		entity2.setStatus(ReservationStatus.FULFILLED);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Reservation> entity = repository.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Reservation entity3 = new Reservation();
		entity3.setReservationDate(LocalDate.of(2020, 2, 1));
		entity3.setStatus(ReservationStatus.FULFILLED);

		Reservation savedEntity = repository.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getReservationDate()).isEqualTo(LocalDate.of(2020, 2, 1));
		assertThat(savedEntity.getStatus()).isEqualTo(ReservationStatus.FULFILLED);
	}

	@Test
	void testDeleteById() {
		repository.deleteById(1L);

		Optional<Reservation> deletedEntity = repository.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByReservationDate() {
		List<Reservation> entities = repository.findByReservationDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getReservationDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByStatus() {
		List<Reservation> entities = repository.findByStatus(ReservationStatus.PENDING);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getStatus()).isEqualTo(ReservationStatus.PENDING);
	}

}
