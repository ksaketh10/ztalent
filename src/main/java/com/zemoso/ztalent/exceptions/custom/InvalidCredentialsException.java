package com.zemoso.ztalent.exceptions.custom;

public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException() {
		super("Email or Password is wrong");
	}
}
