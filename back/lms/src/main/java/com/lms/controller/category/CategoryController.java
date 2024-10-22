package com.lms.controller.category;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.category.CategoryService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/category")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController extends CategoryControllerBasic {

	public CategoryController(CategoryService service) {
		super(service);
	}

}
