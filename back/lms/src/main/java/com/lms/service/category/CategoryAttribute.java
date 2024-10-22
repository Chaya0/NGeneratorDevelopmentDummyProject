package com.lms.service.category;

import com.lms.service.EntityAttribute;

public enum CategoryAttribute implements EntityAttribute {
	NAME("name"),
	BOOK("book"),
	;

	private final String name;

	CategoryAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
