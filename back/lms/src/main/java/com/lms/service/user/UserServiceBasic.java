package com.lms.service.user;

import com.lms.model.User;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.user.UserRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.*;

public class UserServiceBasic extends GenericService<User> implements UserDetailsService {
	protected final UserRepository repository;

	public UserServiceBasic(UserRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public User insert(User object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public User update(User object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	public User findByUsername(String username) {
		return repository.findByUsername(username).orElseThrow();
	}
	public List<User> findByPassword(String password) {
		return repository.findByPassword(password);
	}
	public User findByEmail(String email) {
		return repository.findByEmail(email).orElseThrow();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> myUser = repository.findByUsername(username);
		if (myUser.isEmpty()) {
			throw new UsernameNotFoundException("User name " + username + " not found");
		}
		return new org.springframework.security.core.userdetails.User(myUser.get().getUsername(), myUser.get().getPassword(), myUser.get().getRole().getPermissionList());
	}

}
