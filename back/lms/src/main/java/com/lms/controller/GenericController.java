package com.lms.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import java.util.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;
import com.lms.exceptions.LmsException;
import com.lms.service.GenericService;
import com.lms.utils.ApiUtil;
import com.lms.specification.SpecificationBasic;
import com.lms.specification.SearchDTO;

public class GenericController<T> {
	private final GenericService<T> service;

	public GenericController(GenericService<T> service) {
		this.service = service;
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchPost(@RequestBody SearchDTO request) throws LmsException {
		SpecificationBasic<T> specification = request.buildSpecification(service.getEntityClass());
		return ResponseEntity.ok(service.findAll(specification.getSpecification()));
	}

	@PostMapping(value = "/searchPageable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchPageablePost(@RequestBody SearchDTO request) throws LmsException {
		SpecificationBasic<T> specification = request.buildSpecification(service.getEntityClass());
		return ResponseEntity.ok(service.findAll(specification.getSpecification(), request.createPageable()));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		Optional<T> optional = service.findById(id);
		if (optional.isPresent()) {
			service.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		throw new EntityNotFoundException("id:" + id.toString());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		Optional<T> optional = service.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}
		throw new EntityNotFoundException("id:" + id.toString());
	}

	@PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@Valid @RequestBody T object) throws LmsException {
		return ResponseEntity.ok(service.update(object));
	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> insert(@Valid @RequestBody T object) throws LmsException {
		return ResponseEntity.ok(service.insert(object));
	}

}
