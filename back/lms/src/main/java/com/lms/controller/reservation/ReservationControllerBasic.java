package com.lms.controller.reservation;

import com.lms.service.reservation.ReservationService;
import com.lms.controller.GenericController;
import com.lms.model.Reservation;
import com.lms.exceptions.LmsException;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.lms.specification.SearchDTO;
import com.lms.utils.SecurityUtils;

public class ReservationControllerBasic extends GenericController<Reservation> {
	protected ReservationService reservationService;

	public ReservationControllerBasic(ReservationService service) {
		super(service);
		this.reservationService = service;
	}

	@Override
	public ResponseEntity<?> findAll() {
		SecurityUtils.checkAuthority("can_view_reservation");
		return super.findAll();
	}

	@Override
	public ResponseEntity<?> searchPost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_reservation");
		return super.searchPost(request);
	}

	@Override
	public ResponseEntity<?> searchPageablePost(SearchDTO request) throws LmsException {
		SecurityUtils.checkAuthority("can_view_reservation");
		return super.searchPageablePost(request);
	}

	@Override
	public ResponseEntity<?> delete(Long id) {
		SecurityUtils.checkAuthority("can_delete_reservation");
		return super.delete(id);
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		SecurityUtils.checkAuthority("can_view_reservation");
		return super.getById(id);
	}

	@Override
	public ResponseEntity<?> update(@Valid Reservation object) throws LmsException {
		SecurityUtils.checkAuthority("can_update_reservation");
		return super.update(object);
	}

	@Override
	public ResponseEntity<?> insert(@Valid Reservation object) throws LmsException {
		SecurityUtils.checkAuthority("can_create_reservation");
		return super.insert(object);
	}


}
