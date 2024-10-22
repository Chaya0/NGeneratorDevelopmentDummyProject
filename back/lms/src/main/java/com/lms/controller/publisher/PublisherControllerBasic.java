package com.lms.controller.publisher;

import com.lms.service.publisher.PublisherService;
import com.lms.controller.GenericController;
import com.lms.model.Publisher;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class PublisherControllerBasic extends GenericController<Publisher> {
	protected PublisherService publisherService;

	public PublisherControllerBasic(PublisherService service) {
		super(service);
		this.publisherService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_publisher");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_publisher");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_publisher");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_publisher");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_publisher");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Publisher object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_publisher");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Publisher object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_publisher");
		return super.insert(object);
	}


}
