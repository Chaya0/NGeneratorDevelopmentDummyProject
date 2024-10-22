package com.lms.service.checkout;

import com.lms.service.EntityAttribute;

public enum CheckoutAttribute implements EntityAttribute {
	CHECKOUT_DATE("checkoutDate"),
	DUE_DATE("dueDate"),
	RETURN_DATE("returnDate"),
	STATUS("status"),
	USER("user"),
	BOOK("book"),
	;

	private final String name;

	CheckoutAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
