package com.lms.controller.book;

import com.lms.service.book.BookService;
import com.lms.controller.GenericController;
import com.lms.model.Book;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class BookControllerBasic extends GenericController<Book> {
	protected BookService bookService;

	public BookControllerBasic(BookService service) {
		super(service);
		this.bookService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_book");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_book");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_book");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_book");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_book");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Book object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_book");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Book object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_book");
		return super.insert(object);
	}


}
