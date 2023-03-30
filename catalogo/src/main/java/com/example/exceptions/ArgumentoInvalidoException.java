package com.example.exceptions;

public class ArgumentoInvalidoException extends RuntimeException {

	public ArgumentoInvalidoException() {
		super();
	}

	public ArgumentoInvalidoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ArgumentoInvalidoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ArgumentoInvalidoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ArgumentoInvalidoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
