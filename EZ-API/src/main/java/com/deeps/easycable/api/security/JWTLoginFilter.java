package com.deeps.easycable.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		log.info("Attemp authentication for user >>"+req.getInputStream());
		AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
		log.info("User Credential- Name**" + creds.getUsername() + "**Password***" +"*********");
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
				creds.getPassword()));	
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		List<? extends GrantedAuthority> userDetails=authorities.stream().collect(Collectors.toList());			  
		JWTTokenService.addAuthentication(res, auth.getName(),userDetails);		
	}	
}