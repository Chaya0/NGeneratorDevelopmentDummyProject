package com.lms.service.permission;

import com.lms.model.Permission;
import com.lms.service.GenericService;
import com.lms.exceptions.LmsException;
import com.lms.repository.permission.PermissionRepository;
import java.util.*;
import java.time.*;
import jakarta.persistence.EntityNotFoundException;

public class PermissionServiceBasic extends GenericService<Permission> {
	protected final PermissionRepository repository;

	public PermissionServiceBasic(PermissionRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Permission insert(Permission object) throws LmsException {
		if(object.getId() != null) throw new LmsException("Entity already exists: " + object.getId());
		return repository.save(object);
	}

	@Override
	public Permission update(Permission object) throws LmsException {
		if(object.getId() == null || repository.findById(object.getId()).isEmpty()) throw new EntityNotFoundException(String.valueOf(object.getId()));
		return repository.save(object);
	}

	@Override
	public Class<Permission> getEntityClass() {
		return Permission.class;
	}

	public Permission findByName(String name) {
		return repository.findByName(name).orElseThrow();
	}
	public List<Permission> findByDescription(String description) {
		return repository.findByDescription(description);
	}

}
