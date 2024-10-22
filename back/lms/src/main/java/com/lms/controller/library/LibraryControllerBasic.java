package com.lms.controller.library;

import com.lms.service.library.LibraryService;
import com.lms.controller.GenericController;
import com.lms.model.Library;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class LibraryControllerBasic extends GenericController<Library> {
	protected LibraryService libraryService;

	public LibraryControllerBasic(LibraryService service) {
		super(service);
		this.libraryService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_library");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_library");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_library");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_library");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_library");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Library object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_library");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Library object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_library");
		return super.insert(object);
	}


}
