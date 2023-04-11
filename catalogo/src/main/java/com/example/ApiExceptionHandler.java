package com.example;

import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;

@RestControllerAdvice
public class ApiExceptionHandler {
	// https://datatracker.ietf.org/doc/html/rfc7807
	// Content-Type: application/problem+json
	@JsonInclude(value = Include.NON_EMPTY)
	public static class ErrorMessage extends ProblemDetail {
		private static final long serialVersionUID = 1L;
		private Map<String, String> errors;

		public ErrorMessage(int status, String title) {
			this(status, title, null, null);
		}

		public ErrorMessage(int status, String title, String detail) {
			this(status, title, detail, null);
		}

		public ErrorMessage(int status, String title, Map<String, String> errors) {
			this(status, title, null, errors);
		}

		public ErrorMessage(int status, String title, String detail, Map<String, String> errors) {
			this(getTypeURL(status), status, title, detail, errors);
		}

		public ErrorMessage(String type, int status, String title, String detail, Map<String, String> errors) {
			super(status);
			try {
				this.setType(new URI(type));
			} catch (URISyntaxException e) {
				try {
					this.setType(new URI("about:blank"));
				} catch (URISyntaxException e1) {
				}
			}
			this.setTitle(title); 
			this.setDetail(detail);
			this.errors = errors;
		}
		public Map<String, String> getErrors() {
			return errors;
		}

		public static String getTypeURL(int status) {
			switch (status) {
			case 400:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.1";
			case 401:
				return "https://datatracker.ietf.org/doc/html/rfc7235#section-3.1";
			case 402:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.2";
			case 403:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.3";
			case 404:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.4";
			case 405:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.5";
			case 406:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.6";
			case 407:
				return "https://datatracker.ietf.org/doc/html/rfc7235#section-3.2";
			case 408:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.7";
			case 409:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.8";
			case 410:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.9";
			case 411:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.10";
			case 412:
				return "https://datatracker.ietf.org/doc/html/rfc7232#section-4.2";
			case 413:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.11";
			case 414:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.12";
			case 415:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.13";
			case 416:
				return "https://datatracker.ietf.org/doc/html/rfc7233#section-4.4";
			case 417:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.14";
			case 426:
				return "https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.15";
			default:
				return "about:blank";
			}
		}
	}

	@ExceptionHandler({ ErrorResponseException.class, HttpRequestMethodNotSupportedException.class })
	public ProblemDetail defaultResponse(ErrorResponse exception) {
		return exception.getBody();
	}

//	@ExceptionHandler({ NotFoundException.class })
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ErrorMessage notFoundRequest(Exception exception) {
//		return new ErrorMessage(404, exception.getMessage(), ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), null);
//	}

	@ExceptionHandler({ NotFoundException.class })
	public ProblemDetail notFoundRequest(Exception exception) {
		return ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ BadRequestException.class, DuplicateKeyException.class, HttpMessageNotReadableException.class,
		MethodArgumentTypeMismatchException.class})
	public ProblemDetail badRequest(Exception exception) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler({ InvalidDataException.class, MethodArgumentNotValidException.class })
	public ProblemDetail invalidData(Exception exception) {
		var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Datos invalidos");
		if (exception instanceof InvalidDataException ex && ex.hasErrors()) {
//			ex.getErrors().forEach((n, v) -> problem.setProperty(n, v));
			problem.setProperty("errors", ex.getErrors());
		} else if (exception instanceof BindException ex && ex.hasFieldErrors()) {
//			ex.getFieldErrors().forEach(item -> problem.setProperty(item.getField(), item.getDefaultMessage()));
			var errors = new HashMap<String, String>();
			ex.getFieldErrors().forEach(item -> errors.put(item.getField(), item.getDefaultMessage()));
			problem.setProperty("errors", errors);
			problem = new ErrorMessage(400, "Datos invalidos","",errors);
		}
		return problem;
	}

//	@ExceptionHandler({ AccessDeniedException.class, JWTVerificationException.class, FeignException.Forbidden.class })
//	public ProblemDetail accessDenied(Exception exception) {
//		return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
//	}
//
//	@ExceptionHandler({ FeignException.ServiceUnavailable.class, RetryableException.class })
//	public ProblemDetail conflict(Exception exception) {
//		return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
//	}

//	@ExceptionHandler({ Exception.class })
//	public ProblemDetail unknow(Exception exception) {
//		return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
//	}

}