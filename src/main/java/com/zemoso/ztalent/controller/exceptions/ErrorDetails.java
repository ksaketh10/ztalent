package com.zemoso.ztalent.controller.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Setter @Getter @ToString
public class ErrorDetails {
	private HttpStatus status;
	private String timeStamp;
	private String message;
	private String debugMessage;

	public ErrorDetails(HttpStatus status, String message, Throwable ex) {
		this.setStatus(status);
		this.setMessage(message !=null ? message : ex.getMessage());
		this.setDebugMessage(ex.getLocalizedMessage());
	}

	public ErrorDetails(HttpStatus status, Throwable ex) {
		this.setStatus(status);
		this.setMessage(ex.getMessage());
		this.setDebugMessage(ex.getLocalizedMessage());
	}

	public ErrorDetails() {}
}
