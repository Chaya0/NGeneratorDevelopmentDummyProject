package com.lms.controller.category;

import com.lms.service.category.CategoryService;
import com.lms.controller.GenericController;
import com.lms.model.Category;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class CategoryControllerBasic extends GenericController<Category> {
	protected CategoryService categoryService;

	public CategoryControllerBasic(CategoryService service) {
		super(service);
		this.categoryService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_category");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_category");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_category");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_category");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_category");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Category object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_category");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Category object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_category");
		return super.insert(object);
	}


}
