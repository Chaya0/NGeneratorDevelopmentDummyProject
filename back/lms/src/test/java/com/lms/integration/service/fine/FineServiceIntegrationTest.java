package com.lms.integration.service.fine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.fine.FineRepository;
import com.lms.service.fine.FineService;
import com.lms.model.Fine;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FineServiceIntegrationTest {

	@Autowired
	private FineRepository repository;

	@Autowired
	private FineService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Fine entity1 = new Fine();
		entity1.setFineAmount(1.00);
		entity1.setPaid(true);
		entity1.setFineDate(LocalDate.of(2020, 1, 1));
		entity1.setId(1L);

		Fine entity2 = new Fine();
		entity2.setFineAmount(2.00);
		entity2.setPaid(false);
		entity2.setFineDate(LocalDate.of(2020, 2, 1));
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Fine> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Fine entity3 = new Fine();
		entity3.setFineAmount(3.00);
		entity3.setPaid(false);
		entity3.setFineDate(LocalDate.of(2020, 2, 1));

		Fine savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getFineAmount()).isEqualTo(3.00);
		assertThat(savedEntity.getPaid()).isEqualTo(false);
		assertThat(savedEntity.getFineDate()).isEqualTo(LocalDate.of(2020, 2, 1));
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Fine> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByFineAmount() {
		List<Fine> entities = service.findByFineAmount(1.00);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFineAmount()).isEqualTo(1.00);
	}

	@Test
	void testFindByPaid() {
		List<Fine> entities = service.findByPaid(true);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPaid()).isEqualTo(true);
	}

	@Test
	void testFindByFineDate() {
		List<Fine> entities = service.findByFineDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFineDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

}
