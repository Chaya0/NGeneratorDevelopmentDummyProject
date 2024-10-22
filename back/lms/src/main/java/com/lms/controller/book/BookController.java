package com.lms.controller.book;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.book.BookService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/book")
@SecurityRequirement(name = "bearerAuth")
public class BookController extends BookControllerBasic {

	public BookController(BookService service) {
		super(service);
	}

}
