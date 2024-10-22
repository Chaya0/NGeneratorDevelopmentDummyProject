package com.lms.controller.role;

import com.lms.service.role.RoleService;
import com.lms.controller.GenericController;
import com.lms.model.Role;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class RoleControllerBasic extends GenericController<Role> {
	protected RoleService roleService;

	public RoleControllerBasic(RoleService service) {
		super(service);
		this.roleService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_role");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_role");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_role");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_role");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_role");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Role object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_role");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Role object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_role");
		return super.insert(object);
	}


}
