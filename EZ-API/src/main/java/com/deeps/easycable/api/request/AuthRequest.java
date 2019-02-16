package com.deeps.easycable.api.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AuthRequest {

	@NotNull
	private String userName;
	
	@NotNull
	private String password;
}
