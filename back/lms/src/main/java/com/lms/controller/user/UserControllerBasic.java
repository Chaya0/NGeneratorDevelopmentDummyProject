package com.lms.controller.user;

import com.lms.service.user.UserService;
import com.lms.controller.GenericController;
import com.lms.model.User;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class UserControllerBasic extends GenericController<User> {
	protected UserService userService;

	public UserControllerBasic(UserService service) {
		super(service);
		this.userService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_user");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_user");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_user");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_user");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_user");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid User object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_user");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid User object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_user");
		return super.insert(object);
	}


}
