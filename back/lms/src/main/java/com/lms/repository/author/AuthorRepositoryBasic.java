package com.lms.repository.author;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Author;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface AuthorRepositoryBasic extends GenericRepository<Author> {

	List<Author> findByFirstName(String firstName);
	List<Author> findByLastName(String lastName);
	List<Author> findByBirthDate(LocalDate birthDate);

}
