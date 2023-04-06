package com.example;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;

@RestControllerAdvice
public class ApiExceptionHandler {
	// https://datatracker.ietf.org/doc/html/rfc7807
	// Content-Type: application/problem+json
	@JsonInclude(value = Include.NON_EMPTY)
	public static class ErrorMessage implements Serializable {
		private static final long serialVersionUID = 1L;
		private String type, title, detail;
		private int status;
		private Map<String, String> errors;
		

		public ErrorMessage(int status, String title) {
			this(status,title, null, null);
		}
		public ErrorMessage(int status, String title, String detail) {
			this(status,title, detail, null);
		}
		public ErrorMessage(int status, String title, Map<String, String> errors) {
			this(status,title, null, errors);
		}
		public ErrorMessage(int status, String title, String detail, Map<String, String> errors) {
			this(getTypeURL(status),status,title, detail, errors);
		}

		public ErrorMessage(String type, int status, String title, String detail, Map<String, String> errors) {
			super();
			this.type = type;
			this.title = title;
			this.detail = detail;
			this.status = status;
			this.errors = errors;
		}

		public String getType() { return type; }
		public String getTitle() { return title; }
		public String getDetail() { return detail; }
		public int getStatus() { return status; }
		public Map<String, String> getErrors() { return errors; }
		
		public static String getTypeURL(int status) {
			switch (status) {
				case 400: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.1";
				case 401: return "https://datatracker.ietf.org/doc/html/rfc7235#section-3.1";
				case 402: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.2";
				case 403: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.3";
				case 404: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.4";
				case 405: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.5";
				case 406: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.6";
				case 407: return "https://datatracker.ietf.org/doc/html/rfc7235#section-3.2";
				case 408: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.7";
				case 409: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.8";
				case 410: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.9";
				case 411: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.10";
				case 412: return "https://datatracker.ietf.org/doc/html/rfc7232#section-4.2";
				case 413: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.11";
				case 414: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.12";
				case 415: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.13";
				case 416: return "https://datatracker.ietf.org/doc/html/rfc7233#section-4.4";
				case 417: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.14";
				case 426: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.15";
				default: return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5";
			}
		}
	}

	@ExceptionHandler({ NotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage notFoundRequest(HttpServletRequest request, Exception exception) {
		return new ErrorMessage(404, exception.getMessage(), request.getRequestURI(), null);
	}

	@ExceptionHandler({ BadRequestException.class, DuplicateKeyException.class, HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage badRequest(Exception exception) {
		return new ErrorMessage(400, exception.getMessage(), null, null);
	}

	@ExceptionHandler({ InvalidDataException.class, MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage invalidData(Exception exception) {
		Map<String, String> errors = null;
		if(exception instanceof InvalidDataException ex && ex.hasErrors()) {
			errors = ex.getErrors();
		} else if(exception instanceof BindException ex && ex.hasFieldErrors()) {
			errors = new HashMap<>();
			for(var item: ex.getFieldErrors())
				errors.put(item.getField(), item.getDefaultMessage());
		}
		return new ErrorMessage(400, "Datos invalidos", exception.getMessage(), errors);
	}


	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ErrorMessage methodNotSupported(BindException exception) {
		return new ErrorMessage(405, exception.getMessage(), null, null);
	}

}
