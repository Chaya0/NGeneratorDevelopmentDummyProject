package com.lms.controller.fine;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.fine.FineService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/fine")
@SecurityRequirement(name = "bearerAuth")
public class FineController extends FineControllerBasic {

	public FineController(FineService service) {
		super(service);
	}

}
