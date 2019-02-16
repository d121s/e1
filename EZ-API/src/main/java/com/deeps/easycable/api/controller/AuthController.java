package com.deeps.easycable.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.request.AuthRequest;
import com.deeps.easycable.api.response.AuthResponse;
import com.deeps.easycable.api.service.OperatorServices;

@RestController
public class AuthController{
	
	@Autowired
	OperatorServices oprServices;

	@PostMapping("/login")
	public AuthResponse validateUser(@Valid @RequestBody AuthRequest authRequest) {
		return oprServices.validateUser(authRequest.getUserName(), authRequest.getPassword());
	}

}
