package com.deeps.easycable.api.exception;

public class NotValidRequest extends RuntimeException {
	 private static final long serialVersionUID = 1L;

	    public NotValidRequest(String details) {
	        super("Invalid Data  : " + details);
	    }
}