package com.lms.service.publisher;

import com.lms.model.Publisher;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.publisher.PublisherRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;

public class PublisherServiceBasic extends GenericService<Publisher> {
	protected final PublisherRepository repository;

	public PublisherServiceBasic(PublisherRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Publisher insert(Publisher object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Publisher update(Publisher object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Publisher> getEntityClass() {
		return Publisher.class;
	}

	public List<Publisher> findByName(String name) {
		return repository.findByName(name);
	}
	public List<Publisher> findByAddress(String address) {
		return repository.findByAddress(address);
	}
	public List<Publisher> findByWebsite(String website) {
		return repository.findByWebsite(website);
	}

}
