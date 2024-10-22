package com.lms.service.reservation;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.reservation.ReservationRepository;

@Service
public class ReservationService extends ReservationServiceBasic {

	public ReservationService(ReservationRepository repository) {
		super(repository);
	}
}
