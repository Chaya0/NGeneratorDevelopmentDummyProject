package com.lms.controller.permission;

import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.lms.service.permission.PermissionService;

@Primary
@CrossOrigin
@RestController
@RequestMapping("/api/permission")
@SecurityRequirement(name = "bearerAuth")
public class PermissionController extends PermissionControllerBasic {

	public PermissionController(PermissionService service) {
		super(service);
	}

}
