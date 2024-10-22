package com.lms.controller.author;

import com.lms.service.author.AuthorService;
import com.lms.controller.GenericController;
import com.lms.model.Author;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class AuthorControllerBasic extends GenericController<Author> {
	protected AuthorService authorService;

	public AuthorControllerBasic(AuthorService service) {
		super(service);
		this.authorService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_author");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_author");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_author");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_author");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_author");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Author object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_author");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Author object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_author");
		return super.insert(object);
	}


}
