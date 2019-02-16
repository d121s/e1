package com.deeps.easycable.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

	private Long userId;
	
	private String userName;
	
	private String userRole;
	
	private String authToken;	
	
	private String userStatus;
	
	private boolean isAuthenticated;
}
