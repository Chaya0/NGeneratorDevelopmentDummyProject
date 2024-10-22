package com.lms.service.permission;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.permission.PermissionRepository;

@Service
public class PermissionService extends PermissionServiceBasic {

	public PermissionService(PermissionRepository repository) {
		super(repository);
	}
}
