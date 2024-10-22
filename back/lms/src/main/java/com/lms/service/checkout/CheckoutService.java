package com.lms.service.checkout;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import com.lms.repository.checkout.CheckoutRepository;

@Service
public class CheckoutService extends CheckoutServiceBasic {

	public CheckoutService(CheckoutRepository repository) {
		super(repository);
	}
}
