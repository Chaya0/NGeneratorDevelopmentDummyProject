package com.lms.repository.user;

import org.springframework.data.repository.NoRepositoryBean;
import com.lms.model.User;
import com.lms.repository.GenericRepository;
import java.util.*;
import java.time.*;

@NoRepositoryBean
public interface UserRepositoryBasic extends GenericRepository<User> {

	Optional<User> findByUsername(String username);
	List<User> findByPassword(String password);
	Optional<User> findByEmail(String email);

}
