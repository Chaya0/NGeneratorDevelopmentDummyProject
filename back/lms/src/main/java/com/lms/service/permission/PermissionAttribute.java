package com.lms.service.permission;

import com.lms.service.EntityAttribute;

public enum PermissionAttribute implements EntityAttribute {
	NAME("name"),
	DESCRIPTION("description"),
	;

	private final String name;

	PermissionAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
