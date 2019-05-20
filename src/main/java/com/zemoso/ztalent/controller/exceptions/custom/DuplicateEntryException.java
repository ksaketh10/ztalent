package com.zemoso.ztalent.controller.exceptions.custom;

public class DuplicateEntryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateEntryException() {
		super("Record already exists");
	}
}
