package com.lms.service.library;

import com.lms.model.Library;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.library.LibraryRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;

public class LibraryServiceBasic extends GenericService<Library> {
	protected final LibraryRepository repository;

	public LibraryServiceBasic(LibraryRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Library insert(Library object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Library update(Library object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Library> getEntityClass() {
		return Library.class;
	}

	public List<Library> findByName(String name) {
		return repository.findByName(name);
	}
	public List<Library> findByAddress(String address) {
		return repository.findByAddress(address);
	}
	public List<Library> findByCity(String city) {
		return repository.findByCity(city);
	}
	public List<Library> findByPhone(String phone) {
		return repository.findByPhone(phone);
	}

}
