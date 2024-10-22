package com.lms.controller.checkout;

import com.lms.service.checkout.CheckoutService;
import com.lms.controller.GenericController;
import com.lms.model.Checkout;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class CheckoutControllerBasic extends GenericController<Checkout> {
	protected CheckoutService checkoutService;

	public CheckoutControllerBasic(CheckoutService service) {
		super(service);
		this.checkoutService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_checkout");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_checkout");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_checkout");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_checkout");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_checkout");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Checkout object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_checkout");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Checkout object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_checkout");
		return super.insert(object);
	}


}
