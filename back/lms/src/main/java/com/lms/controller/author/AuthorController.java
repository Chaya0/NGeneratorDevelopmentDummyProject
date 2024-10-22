package com.lms.controller.author;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.author.AuthorService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/author")
@SecurityRequirement(name = "bearerAuth")
public class AuthorController extends AuthorControllerBasic {

	public AuthorController(AuthorService service) {
		super(service);
	}

}
