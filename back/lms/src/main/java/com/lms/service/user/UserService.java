package com.lms.service.user;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.user.UserRepository;

@Service
public class UserService extends UserServiceBasic {

	public UserService(UserRepository repository) {
		super(repository);
	}
}
