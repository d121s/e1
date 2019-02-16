package com.deeps.easycable.api.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.deeps.easycable.api.exception.UnAuthorizedTenantException;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CustomAuthenticationEntryHandler implements AuthenticationEntryPoint, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4842562886130059358L;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// 401
		log.error(authException.getLocalizedMessage());		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed."+ ((response.getHeader("Exception")!=null) ? response.getHeader("Exception"):""));
	}
	
	@ExceptionHandler(value = {UnAuthorizedTenantException.class })
	public void commence(HttpServletRequest request, HttpServletResponse response,
			UnAuthorizedTenantException accessDeniedException) throws IOException, ServletException {
		// 403
		log.error(accessDeniedException);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Authorization Failed : " + accessDeniedException.getMessage());
	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 403
		log.error(accessDeniedException);
		response.sendError(HttpServletResponse.SC_FORBIDDEN,
				"Authorization Failed : " + accessDeniedException.getMessage());
	}

	@ExceptionHandler(value = { Exception.class })
	public void commence(HttpServletRequest request, HttpServletResponse response, Exception exception)
			throws IOException, ServletException {
		// 500
		log.error(exception);
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				"Internal Server Error : " + exception.getMessage());
	}

}
