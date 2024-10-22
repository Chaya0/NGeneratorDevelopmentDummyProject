package com.lms.controller.publisher;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.publisher.PublisherService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/publisher")
@SecurityRequirement(name = "bearerAuth")
public class PublisherController extends PublisherControllerBasic {

	public PublisherController(PublisherService service) {
		super(service);
	}

}
