package com.lms.integration.service.checkout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.lms.repository.checkout.CheckoutRepository;
import com.lms.service.checkout.CheckoutService;
import com.lms.model.Checkout;
import com.lms.model.enums.CheckoutStatus;

import java.util.*;
import java.time.*;

@ActiveProfiles("test")
@SpringBootTest  // This starts the full Spring Boot context for service integration tests
@Transactional   // Ensures that each test is rolled back automatically
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckoutServiceIntegrationTest {

	@Autowired
	private CheckoutRepository repository;

	@Autowired
	private CheckoutService service;

	@BeforeEach
	void setUp() {
	repository.deleteAll();
		Checkout entity1 = new Checkout();
		entity1.setCheckoutDate(LocalDateTime.of(2020, 1, 1, 0, 0));
		entity1.setDueDate(LocalDate.of(2020, 1, 1));
		entity1.setReturnDate(LocalDate.of(2020, 1, 1));
		entity1.setStatus(CheckoutStatus.BORROWED);
		entity1.setId(1L);

		Checkout entity2 = new Checkout();
		entity2.setCheckoutDate(LocalDateTime.of(2020, 2, 1, 0, 0));
		entity2.setDueDate(LocalDate.of(2020, 2, 1));
		entity2.setReturnDate(LocalDate.of(2020, 2, 1));
		entity2.setStatus(CheckoutStatus.RETURNED);
		entity2.setId(2L);

		repository.save(entity1);
		repository.save(entity2);
	}

	@Test
	void testFindById() {
		Optional<Checkout> entity = service.findById(repository.findAll().get(0).getId());
		assertThat(entity).isPresent();
	}

	@Test
	void testSave() {
		Checkout entity3 = new Checkout();
		entity3.setCheckoutDate(LocalDateTime.of(2020, 2, 1, 0, 0));
		entity3.setDueDate(LocalDate.of(2020, 2, 1));
		entity3.setReturnDate(LocalDate.of(2020, 2, 1));
		entity3.setStatus(CheckoutStatus.RETURNED);

		Checkout savedEntity = service.save(entity3);

		assertThat(savedEntity.getId()).isNotNull();
		assertThat(savedEntity.getCheckoutDate()).isEqualTo(LocalDateTime.of(2020, 2, 1, 0, 0));
		assertThat(savedEntity.getDueDate()).isEqualTo(LocalDate.of(2020, 2, 1));
		assertThat(savedEntity.getReturnDate()).isEqualTo(LocalDate.of(2020, 2, 1));
		assertThat(savedEntity.getStatus()).isEqualTo(CheckoutStatus.RETURNED);
	}

	@Test
	void testDeleteById() {
		service.deleteById(1L);

		Optional<Checkout> deletedEntity = service.findById(1L);
		assertThat(deletedEntity).isNotPresent();
	}

	@Test
	void testFindByCheckoutDate() {
		List<Checkout> entities = service.findByCheckoutDate(LocalDateTime.of(2020, 1, 1, 0, 0));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getCheckoutDate()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0));
	}

	@Test
	void testFindByDueDate() {
		List<Checkout> entities = service.findByDueDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getDueDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByReturnDate() {
		List<Checkout> entities = service.findByReturnDate(LocalDate.of(2020, 1, 1));
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getReturnDate()).isEqualTo(LocalDate.of(2020, 1, 1));
	}

	@Test
	void testFindByStatus() {
		List<Checkout> entities = service.findByStatus(CheckoutStatus.BORROWED);
		assertThat(entities).hasSize(1);
		assertThat(entities.get(0).getStatus()).isEqualTo(CheckoutStatus.BORROWED);
	}

}
