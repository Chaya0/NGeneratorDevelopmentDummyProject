package com.lms.service.user;

import com.lms.service.EntityAttribute;

public enum UserAttribute implements EntityAttribute {
	USERNAME("username"),
	PASSWORD("password"),
	EMAIL("email"),
	CHECKOUT("checkout"),
	FINE("fine"),
	ROLE("role"),
	;

	private final String name;

	UserAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
