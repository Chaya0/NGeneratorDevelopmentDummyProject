package com.lms.repository.reservation;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Reservation;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;
import com.lms.model.enums.ReservationStatus;

@NoRepositoryBean
public interface ReservationRepositoryBasic extends GenericRepository<Reservation> {

	List<Reservation> findByReservationDate(LocalDate reservationDate);
	List<Reservation> findByStatus(ReservationStatus status);

}
