package com.lms.service.author;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.author.AuthorRepository;

@Service
public class AuthorService extends AuthorServiceBasic {

	public AuthorService(AuthorRepository repository) {
		super(repository);
	}
}
