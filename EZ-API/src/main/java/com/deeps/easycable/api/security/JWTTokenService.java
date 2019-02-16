package com.deeps.easycable.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.deeps.easycable.api.ApiUtils;
import com.deeps.easycable.api.response.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;
import java.io.IOException;

@Component
@Log4j2
public class JWTTokenService {
	
	@Autowired
	ApiUtils apiUtils;

	private static long expiryTime;	
	@Value("${restapi.security.session.token.expirytime}")
	public void setExpiryTime(long expiryTime) {
		JWTTokenService.expiryTime = expiryTime;
	}

	private static String tokenprefix;	
	@Value("${restapi.security.session.token.tokenprefix}")
	public void setTokenprefix(String tokenprefix) {
		JWTTokenService.tokenprefix = tokenprefix;
	}

	private static String authenticationHeaderString;
	@Value("${restapi.security.session.token.authenticationHeaderString}")
	public void setAuthenticationHeaderString(String authenticationHeaderString) {
		JWTTokenService.authenticationHeaderString = authenticationHeaderString;
	}
	
	public static void addAuthentication(HttpServletResponse res, String username,List<? extends GrantedAuthority> userDtls) {
		log.info("Setting Expiry Time for user session "+expiryTime+"ms");
		ObjectMapper mapper = new ObjectMapper();
		String strJWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiryTime))
				.signWith(SignatureAlgorithm.HS512, ApiUtils.getSecretkey()).compact();
		res.addHeader(authenticationHeaderString, tokenprefix + " " + strJWT);
		AuthResponse authResponse=new AuthResponse(Long.parseLong(userDtls.get(1).getAuthority()), username, userDtls.get(0).getAuthority(), strJWT, null, true);
		/* Logic to handle Bearer Token in Body */
		try {			
			res.getWriter().append(mapper.writeValueAsString(authResponse));
		} catch (IOException e) {
			log.error(e.toString());
		}
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(authenticationHeaderString);
		log.info("Token from header***"+token);
		try{
			if (token != null) {
					// parse the token.
					String user = Jwts.parser()
							.setSigningKey(ApiUtils.getSecretkey())
							.parseClaimsJws(token.replace(tokenprefix, ""))
							.getBody().getSubject();			
				    log.info("User Authenticated Successfully with UserName"+user);
					return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
			}
		}catch(Exception ex){
			log.error("Error in Authentication***"+ex.toString());
		}
		return null;
	}
	
	public static String getUser(HttpServletRequest request){
		String token = request.getHeader(authenticationHeaderString);
		log.info("Token from header***"+token);		
		return Jwts.parser().setSigningKey(ApiUtils.getSecretkey()).parseClaimsJws(token.replace(tokenprefix, ""))
				.getBody().getSubject();
	}
}
