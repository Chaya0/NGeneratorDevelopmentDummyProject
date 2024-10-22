package com.lms.controller.permission;

import com.lms.service.permission.PermissionService;
import com.lms.controller.GenericController;
import com.lms.model.Permission;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class PermissionControllerBasic extends GenericController<Permission> {
	protected PermissionService permissionService;

	public PermissionControllerBasic(PermissionService service) {
		super(service);
		this.permissionService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_permission");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_permission");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_permission");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_permission");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_permission");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Permission object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_permission");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Permission object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_permission");
		return super.insert(object);
	}


}
