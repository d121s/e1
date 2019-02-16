package com.deeps.easycable.api.exception;

public class UnAuthorizedTenantException extends RuntimeException{
	private static final long serialVersionUID = 1L;

    public UnAuthorizedTenantException(String msg) {
        super(msg);
    }
}
