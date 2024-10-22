package com.lms.controller.library;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.library.LibraryService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/library")
@SecurityRequirement(name = "bearerAuth")
public class LibraryController extends LibraryControllerBasic {

	public LibraryController(LibraryService service) {
		super(service);
	}

}
