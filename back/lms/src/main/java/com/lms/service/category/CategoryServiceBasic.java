package com.lms.service.category;

import com.lms.model.Category;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.category.CategoryRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;

public class CategoryServiceBasic extends GenericService<Category> {
	protected final CategoryRepository repository;

	public CategoryServiceBasic(CategoryRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Category insert(Category object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Category update(Category object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Category> getEntityClass() {
		return Category.class;
	}

	public List<Category> findByName(String name) {
		return repository.findByName(name);
	}

}
