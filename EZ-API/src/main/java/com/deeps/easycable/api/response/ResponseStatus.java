package com.deeps.easycable.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseStatus {
	private int code;
	private String message;
}
