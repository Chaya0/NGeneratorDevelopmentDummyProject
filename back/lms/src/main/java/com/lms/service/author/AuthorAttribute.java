package com.lms.service.author;

import com.lms.service.EntityAttribute;

public enum AuthorAttribute implements EntityAttribute {
	FIRST_NAME("firstName"),
	LAST_NAME("lastName"),
	BIRTH_DATE("birthDate"),
	BOOK("book"),
	;

	private final String name;

	AuthorAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
