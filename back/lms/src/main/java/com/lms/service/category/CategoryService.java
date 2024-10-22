package com.lms.service.category;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.category.CategoryRepository;

@Service
public class CategoryService extends CategoryServiceBasic {

	public CategoryService(CategoryRepository repository) {
		super(repository);
	}
}
