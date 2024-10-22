package com.lms.repository.book;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Book;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface BookRepositoryBasic extends GenericRepository<Book> {

	List<Book> findByTitle(String title);
	List<Book> findByIsbn(String isbn);
	List<Book> findByPublishedDate(LocalDate publishedDate);
	List<Book> findByLanguage(String language);
	List<Book> findByAvailableCopies(Integer availableCopies);
	List<Book> findByTotalCopies(Integer totalCopies);

}
