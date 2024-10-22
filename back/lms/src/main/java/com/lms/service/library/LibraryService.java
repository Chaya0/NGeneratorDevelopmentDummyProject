package com.lms.service.library;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.library.LibraryRepository;

@Service
public class LibraryService extends LibraryServiceBasic {

	public LibraryService(LibraryRepository repository) {
		super(repository);
	}
}
