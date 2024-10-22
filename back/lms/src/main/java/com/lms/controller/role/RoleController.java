package com.lms.controller.role;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.role.RoleService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/role")
@SecurityRequirement(name = "bearerAuth")
public class RoleController extends RoleControllerBasic {

	public RoleController(RoleService service) {
		super(service);
	}

}
