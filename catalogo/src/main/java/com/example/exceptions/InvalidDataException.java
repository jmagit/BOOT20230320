package com.example.exceptions;

import java.util.Map;

public class InvalidDataException extends Exception {
	private static final long serialVersionUID = 1L;
	private final static String MESSAGE_STRING = "Invalid data";
	private Map<String, String> errors = null;
	
	public InvalidDataException() {
		this(MESSAGE_STRING);
	}

	public InvalidDataException(String message) {
		super(message);
	}

	public InvalidDataException(Map<String, String> errors) {
		this(MESSAGE_STRING, errors);
	}

	public InvalidDataException(String message, Map<String, String> errors) {
		super(message);
		this.errors = errors;
	}

	public InvalidDataException(Throwable cause) {
		this(MESSAGE_STRING, cause);
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidDataException(Throwable cause, Map<String, String> errors) {
		this(MESSAGE_STRING, cause, errors);
	}

	public InvalidDataException(String message, Throwable cause, Map<String, String> errors) {
		super(message, cause);
		this.errors = errors;
	}

	public InvalidDataException(String message, Throwable cause, Map<String, String> errors, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.errors = errors;
	}

	public boolean hasErrors() { return errors != null; }
	public Map<String, String> getErrors() { return errors; }
	
	
}
