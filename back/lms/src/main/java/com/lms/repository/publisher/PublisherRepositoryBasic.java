package com.lms.repository.publisher;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Publisher;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface PublisherRepositoryBasic extends GenericRepository<Publisher> {

	List<Publisher> findByName(String name);
	List<Publisher> findByAddress(String address);
	List<Publisher> findByWebsite(String website);

}
