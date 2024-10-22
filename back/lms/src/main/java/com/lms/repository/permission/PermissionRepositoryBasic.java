package com.lms.repository.permission;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Permission;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface PermissionRepositoryBasic extends GenericRepository<Permission> {

	Optional<Permission> findByName(String name);
	List<Permission> findByDescription(String description);

}
