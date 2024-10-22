package com.lms.service.role;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.role.RoleRepository;

@Service
public class RoleService extends RoleServiceBasic {

	public RoleService(RoleRepository repository) {
		super(repository);
	}
}
