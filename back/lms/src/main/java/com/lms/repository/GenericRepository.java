package com.lms.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Long> {

	Page<T> findAll(Specification<T> spec, Pageable pageSort);
	List<T> findAll(Specification<T> spec);

}
