package com.lms.service.fine;

import com.lms.model.Fine;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.fine.FineRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;

public class FineServiceBasic extends GenericService<Fine> {
	protected final FineRepository repository;

	public FineServiceBasic(FineRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Fine insert(Fine object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Fine update(Fine object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Fine> getEntityClass() {
		return Fine.class;
	}

	public List<Fine> findByFineAmount(Double fineAmount) {
		return repository.findByFineAmount(fineAmount);
	}
	public List<Fine> findByPaid(Boolean paid) {
		return repository.findByPaid(paid);
	}
	public List<Fine> findByFineDate(LocalDate fineDate) {
		return repository.findByFineDate(fineDate);
	}

}
