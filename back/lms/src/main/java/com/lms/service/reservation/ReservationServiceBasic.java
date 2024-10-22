package com.lms.service.reservation;

import com.lms.model.Reservation;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.reservation.ReservationRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;
import com.lms.model.enums.ReservationStatus;

public class ReservationServiceBasic extends GenericService<Reservation> {
	protected final ReservationRepository repository;

	public ReservationServiceBasic(ReservationRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Reservation insert(Reservation object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Reservation update(Reservation object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Reservation> getEntityClass() {
		return Reservation.class;
	}

	public List<Reservation> findByReservationDate(LocalDate reservationDate) {
		return repository.findByReservationDate(reservationDate);
	}
	public List<Reservation> findByStatus(ReservationStatus status) {
		return repository.findByStatus(status);
	}

}
