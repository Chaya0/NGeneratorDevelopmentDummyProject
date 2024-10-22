package com.lms.service.checkout;

import com.lms.model.Checkout;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.checkout.CheckoutRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;
import com.lms.model.enums.CheckoutStatus;

public class CheckoutServiceBasic extends GenericService<Checkout> {
	protected final CheckoutRepository repository;

	public CheckoutServiceBasic(CheckoutRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Checkout insert(Checkout object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Checkout update(Checkout object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Checkout> getEntityClass() {
		return Checkout.class;
	}

	public List<Checkout> findByCheckoutDate(LocalDateTime checkoutDate) {
		return repository.findByCheckoutDate(checkoutDate);
	}
	public List<Checkout> findByDueDate(LocalDate dueDate) {
		return repository.findByDueDate(dueDate);
	}
	public List<Checkout> findByReturnDate(LocalDate returnDate) {
		return repository.findByReturnDate(returnDate);
	}
	public List<Checkout> findByStatus(CheckoutStatus status) {
		return repository.findByStatus(status);
	}

}
