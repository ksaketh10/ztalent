package com.zemoso.ztalent.exceptions.custom;

public class NoDataFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoDataFoundException() {
			super("Ooops!!!No data Available");
		}

}
