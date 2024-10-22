package com.lms.repository.role;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.Role;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface RoleRepositoryBasic extends GenericRepository<Role> {

	Optional<Role> findByName(String name);
	List<Role> findByDescription(String description);

}
