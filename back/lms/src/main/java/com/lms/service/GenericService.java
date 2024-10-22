package com.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.lms.repository.GenericRepository;
import com.lms.exceptions.LmsException;
import java.util.*;

public abstract class GenericService<T> {
	private final GenericRepository<T> repository;

	public GenericService(GenericRepository<T> repository) {
		this.repository = repository;
	}
	public List<T> findAll() {
		return repository.findAll();
	}

	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<T> findAll(Specification<T> specification) {
		return repository.findAll(specification);
	}

	public Page<T> findAll(Specification<T> specification, Pageable pageable) {
		return repository.findAll(specification, pageable);
	}

	public Optional<T> findById(Long id) {
		return repository.findById(id);
	}

	public abstract T insert(T object) throws LmsException;

	public abstract T update(T object) throws LmsException;

	public T save(T object) {
		return repository.save(object);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public abstract Class<T> getEntityClass();
}
