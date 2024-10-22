package com.lms.service.fine;

import com.lms.service.EntityAttribute;

public enum FineAttribute implements EntityAttribute {
	FINE_AMOUNT("fineAmount"),
	PAID("paid"),
	FINE_DATE("fineDate"),
	USER("user"),
	CHECKOUT("checkout"),
	;

	private final String name;

	FineAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
