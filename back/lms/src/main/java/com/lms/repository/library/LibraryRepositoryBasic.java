package com.lms.repository.library;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Library;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface LibraryRepositoryBasic extends GenericRepository<Library> {

	List<Library> findByName(String name);
	List<Library> findByAddress(String address);
	List<Library> findByCity(String city);
	List<Library> findByPhone(String phone);

}
