package com.lms.repository.category;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Category;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface CategoryRepositoryBasic extends GenericRepository<Category> {

	List<Category> findByName(String name);

}
