package com.lms.exceptions;

public class OperationNotSupportedException extends LmsException {
	private static final long serialVersionUID = 1L;

	public OperationNotSupportedException(String message) {
		super(message);
	}
}
