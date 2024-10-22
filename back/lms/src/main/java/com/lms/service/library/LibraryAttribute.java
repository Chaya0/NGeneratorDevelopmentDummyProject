package com.lms.service.library;

import com.lms.service.EntityAttribute;

public enum LibraryAttribute implements EntityAttribute {
	NAME("name"),
	ADDRESS("address"),
	CITY("city"),
	PHONE("phone"),
	BOOK("book"),
	;

	private final String name;

	LibraryAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
