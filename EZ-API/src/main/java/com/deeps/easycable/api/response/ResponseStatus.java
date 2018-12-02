package com.deeps.easycable.api.response;

public class ResponseStatus {
	private int code;
	private String message;

	public ResponseStatus() {

	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param code
	 * @param message
	 */
	public ResponseStatus(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public static ResponseStatus success() {
		return new ResponseStatus(1, "Success");
	}

	public static ResponseStatus build(String statusMessage, int statusCode) {
		return new ResponseStatus(statusCode, statusMessage);
	}

	public ResponseStatus(Integer statusCode, String statusMessage) {
		this.code = statusCode;
		this.message = statusMessage;
	}

}
