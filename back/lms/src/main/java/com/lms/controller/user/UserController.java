package com.lms.controller.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.user.UserService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController extends UserControllerBasic {

	public UserController(UserService service) {
		super(service);
	}

}
