package com.deeps.easycable.api.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8838537468574045276L;

	public NotFoundException(String msg) {
		super(msg);
	}
}
