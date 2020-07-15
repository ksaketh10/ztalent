package com.zemoso.ztalent.exceptions.custom;

public class InvalidParametersException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidParametersException() {
		super("Invalid Paramaters Or Minimum Paramater are not Set");
	}
}
