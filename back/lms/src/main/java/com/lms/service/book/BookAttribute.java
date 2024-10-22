package com.lms.service.book;

import com.lms.service.EntityAttribute;

public enum BookAttribute implements EntityAttribute {
	TITLE("title"),
	ISBN("isbn"),
	PUBLISHED_DATE("publishedDate"),
	LANGUAGE("language"),
	AVAILABLE_COPIES("availableCopies"),
	TOTAL_COPIES("totalCopies"),
	AUTHOR("author"),
	PUBLISHER("publisher"),
	CATEGORY("category"),
	LIBRARY("library"),
	;

	private final String name;

	BookAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
