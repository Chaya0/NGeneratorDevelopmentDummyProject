package com.lms.service.author;

import com.lms.model.Author;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.author.AuthorRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;

public class AuthorServiceBasic extends GenericService<Author> {
	protected final AuthorRepository repository;

	public AuthorServiceBasic(AuthorRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Author insert(Author object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Author update(Author object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Author> getEntityClass() {
		return Author.class;
	}

	public List<Author> findByFirstName(String firstName) {
		return repository.findByFirstName(firstName);
	}
	public List<Author> findByLastName(String lastName) {
		return repository.findByLastName(lastName);
	}
	public List<Author> findByBirthDate(LocalDate birthDate) {
		return repository.findByBirthDate(birthDate);
	}

}
