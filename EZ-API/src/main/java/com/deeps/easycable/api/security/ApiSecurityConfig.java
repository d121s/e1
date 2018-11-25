package com.deeps.easycable.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

	//@Autowired
	//private AADAuthenticationFilter aadAuthFilter;


	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
	 http.csrf().disable().authorizeRequests().anyRequest().permitAll();
	 }

	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Needs to be validated
		http.cors().disable();
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
						"/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-ui.html")
				.permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilterBefore(aadAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}*/
}
