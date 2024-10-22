package com.lms.service.publisher;

import com.lms.service.EntityAttribute;

public enum PublisherAttribute implements EntityAttribute {
	NAME("name"),
	ADDRESS("address"),
	WEBSITE("website"),
	BOOK("book"),
	;

	private final String name;

	PublisherAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
