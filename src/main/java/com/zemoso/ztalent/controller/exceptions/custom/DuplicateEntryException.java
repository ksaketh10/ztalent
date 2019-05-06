package com.zemoso.ztalent.controller.exceptions.custom;

public class DuplicateEntryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateEntryException() {
		super("Employee already exists with given first name and last name");
	}
}
