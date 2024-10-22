package com.lms.controller.fine;

import com.lms.service.fine.FineService;
import com.lms.controller.GenericController;
import com.lms.model.Fine;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class FineControllerBasic extends GenericController<Fine> {
	protected FineService fineService;

	public FineControllerBasic(FineService service) {
		super(service);
		this.fineService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_fine");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_fine");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_fine");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_fine");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_fine");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Fine object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_fine");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Fine object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_fine");
		return super.insert(object);
	}


}
