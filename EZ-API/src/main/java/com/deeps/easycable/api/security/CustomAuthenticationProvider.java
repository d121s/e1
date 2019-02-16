package com.deeps.easycable.api.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.deeps.easycable.api.response.AuthResponse;
import com.deeps.easycable.api.service.OperatorServices;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private OperatorServices operatorService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		log.debug("Inside Authenticaon filter*********");
		log.debug("User Details to be authenticated*********" + name);

		log.debug("Calling Validation Service for User*********" + name);
		AuthResponse authResponse= operatorService.validateUser(name, password);
		Set<GrantedAuthority> loginUserDtls = new HashSet<>();
		if (authResponse != null && authentication.isAuthenticated()) {
			log.info("logged in User Details" + authResponse.getUserName());
			loginUserDtls.add(new SimpleGrantedAuthority(authResponse.getUserRole()));
			loginUserDtls.add(new SimpleGrantedAuthority(authResponse.getUserId().toString()));
			List<GrantedAuthority> authorities = new ArrayList<>(loginUserDtls);
			return new UsernamePasswordAuthenticationToken(name, password, authorities);
		} else {
			throw new BadCredentialsException("1000");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}