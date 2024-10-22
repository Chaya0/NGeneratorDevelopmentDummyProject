package com.lms.controller.checkout;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.checkout.CheckoutService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/checkout")
@SecurityRequirement(name = "bearerAuth")
public class CheckoutController extends CheckoutControllerBasic {

	public CheckoutController(CheckoutService service) {
		super(service);
	}

}
