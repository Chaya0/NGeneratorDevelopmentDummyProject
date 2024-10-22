package com.lms.integration.repository.fine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.fine.FineRepository;
import com.lms.model.Fine;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FineRepositoryIntegrationTest {

	@Autowired
	private FineRepository repository;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Fine entity1 = new Fine();
		entity1.setFineAmount(1.00);
		entity1.setPaid(true);
		entity1.setFineDate(LocalDate.of(2020, 1, 1));

		Fine entity2 = new Fine();
		entity2.setFineAmount(2.00);
		entity2.setPaid(false);
		entity2.setFineDate(LocalDate.of(2020, 2, 1));

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Fine> entity = repository.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Fine entity3 = new Fine();
		entity3.setFineAmount(3.00);
		entity3.setPaid(false);
		entity3.setFineDate(LocalDate.of(2020, 2, 1));

		Fine savedEntity = repository.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getFineAmount()).isEqualTo(3.00);
		assertThat(savedEntity.getPaid()).isEqualTo(false);
		assertThat(savedEntity.getFineDate()).isEqualTo(LocalDate.of(2020, 2, 1));
	}

	@Test
	void testDeleteById() {
		repository.deleteById(1L);

		Optional<Fine> deletedEntity = repository.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByFineAmount() {
		List<Fine> entities = repository.findByFineAmount(1.00);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFineAmount()).isEqualTo(1.00);
	}

	@Test
	void testFindByPaid() {
		List<Fine> entities = repository.findByPaid(true);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getPaid()).isEqualTo(true);
	}

	@Test
	void testFindByFineDate() {
		List<Fine> entities = repository.findByFineDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getFineDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

}
