package com.lms.service.role;

import com.lms.model.Role;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.role.RoleRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;

public class RoleServiceBasic extends GenericService<Role> {
	protected final RoleRepository repository;

	public RoleServiceBasic(RoleRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Role insert(Role object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Role update(Role object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Role> getEntityClass() {
		return Role.class;
	}

	public Role findByName(String name) {
		return repository.findByName(name).orElseThrow();
	}
	public List<Role> findByDescription(String description) {
		return repository.findByDescription(description);
	}

}
