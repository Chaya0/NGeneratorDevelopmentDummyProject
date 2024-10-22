package com.lms.exceptions;

import java.io.Serial;

public class FieldNotFoundException extends LmsException {
	@Serial
    private static final long serialVersionUID = 1L;

	public FieldNotFoundException(String message) {
        super(message);
    }
}
