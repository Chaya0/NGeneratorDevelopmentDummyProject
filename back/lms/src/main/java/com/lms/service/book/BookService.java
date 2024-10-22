package com.lms.service.book;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.book.BookRepository;

@Service
public class BookService extends BookServiceBasic {

	public BookService(BookRepository repository) {
		super(repository);
	}
}
