package com.lms.service.book;

import com.lms.model.Book;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.book.BookRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;

public class BookServiceBasic extends GenericService<Book> {
	protected final BookRepository repository;

	public BookServiceBasic(BookRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Book insert(Book object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Book update(Book object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Book> getEntityClass() {
		return Book.class;
	}

	public List<Book> findByTitle(String title) {
		return repository.findByTitle(title);
	}
	public List<Book> findByIsbn(String isbn) {
		return repository.findByIsbn(isbn);
	}
	public List<Book> findByPublishedDate(LocalDate publishedDate) {
		return repository.findByPublishedDate(publishedDate);
	}
	public List<Book> findByLanguage(String language) {
		return repository.findByLanguage(language);
	}
	public List<Book> findByAvailableCopies(Integer availableCopies) {
		return repository.findByAvailableCopies(availableCopies);
	}
	public List<Book> findByTotalCopies(Integer totalCopies) {
		return repository.findByTotalCopies(totalCopies);
	}

}
