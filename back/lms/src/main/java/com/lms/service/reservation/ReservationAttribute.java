package com.lms.service.reservation;

import com.lms.service.EntityAttribute;

public enum ReservationAttribute implements EntityAttribute {
	RESERVATION_DATE("reservationDate"),
	STATUS("status"),
	USER("user"),
	BOOK("book"),
	;

	private final String name;

	ReservationAttribute(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
