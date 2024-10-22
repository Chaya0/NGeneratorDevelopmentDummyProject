package com.lms.service.publisher;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.publisher.PublisherRepository;

@Service
public class PublisherService extends PublisherServiceBasic {

	public PublisherService(PublisherRepository repository) {
		super(repository);
	}
}
