package com.lms.controller.reservation;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.reservation.ReservationService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/reservation")
@SecurityRequirement(name = "bearerAuth")
public class ReservationController extends ReservationControllerBasic {

	public ReservationController(ReservationService service) {
		super(service);
	}

}
