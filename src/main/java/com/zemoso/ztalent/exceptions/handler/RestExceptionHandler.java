package com.zemoso.ztalent.exceptions.handler;

import com.zemoso.ztalent.exceptions.ErrorDetails;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.zemoso.ztalent.exceptions.custom.*;

import java.util.function.Function;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final static Function<ErrorDetails, ResponseEntity<Object>> buildResponseEntity = x -> new ResponseEntity<>(
			x, x.getStatus());

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String errorMessage = "Malformed JSON request";
		return buildResponseEntity.apply(new ErrorDetails(
				HttpStatus.BAD_REQUEST, errorMessage, ex));
	}

	@ExceptionHandler(NoDataFoundException.class)
	protected ResponseEntity<Object> dataNotFound(NoDataFoundException ex,
			WebRequest request) {
		return buildResponseEntity.apply(new ErrorDetails(
				HttpStatus.INTERNAL_SERVER_ERROR, ex));
	}

	@ExceptionHandler(InvalidParametersException.class)
	protected ResponseEntity<Object> invalidParams(InvalidParametersException ex,
			WebRequest request) {
		return buildResponseEntity.apply(new ErrorDetails(
				HttpStatus.INTERNAL_SERVER_ERROR, ex));
	}

	@ExceptionHandler(DuplicateEntryException.class)
	protected ResponseEntity<Object> duplicateEntry(DuplicateEntryException ex,
												   WebRequest request) {
		return buildResponseEntity.apply(new ErrorDetails(
				HttpStatus.INTERNAL_SERVER_ERROR, ex));
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	protected ResponseEntity<Object> invalidCredentials(InvalidCredentialsException ex,
													WebRequest request) {
		return buildResponseEntity.apply(new ErrorDetails(
				HttpStatus.INTERNAL_SERVER_ERROR, ex));
	}
}