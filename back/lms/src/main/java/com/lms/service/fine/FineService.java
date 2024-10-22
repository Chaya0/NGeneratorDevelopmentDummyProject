package com.lms.service.fine;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.fine.FineRepository;

@Service
public class FineService extends FineServiceBasic {

	public FineService(FineRepository repository) {
		super(repository);
	}
}
