package com.deeps.easycable.api.response;


public class ServiceResponse {

	private ResponseStatus status;


	/**
	 * @return the status
	 */
	public ResponseStatus getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}


	/**
	 */
	public ServiceResponse() {
		super();
	}


	public ServiceResponse(ResponseStatus responseStatus) {
		this.status=responseStatus;
	}

	
}
