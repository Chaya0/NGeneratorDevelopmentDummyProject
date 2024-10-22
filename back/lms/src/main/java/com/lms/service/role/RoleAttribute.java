package com.lms.service.role;

import com.lms.service.EntityAttribute;

public enum RoleAttribute implements EntityAttribute {
	NAME("name"),
	DESCRIPTION("description"),
	PERMISSION("permission"),
	;

	private final String name;

	RoleAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
