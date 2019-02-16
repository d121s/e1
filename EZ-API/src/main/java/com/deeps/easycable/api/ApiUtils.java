package com.deeps.easycable.api;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ApiUtils {

	@Value("${restapi.security.session.token.expirytime}")
	private long expiryTime;

	private static String secretkey;
	
	public static String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		ApiUtils.secretkey = secretkey;
	}
	
	@PostConstruct
	public void init() {		
			byte[] secretBytes=MacProvider.generateKey().getEncoded();
			String secretkey= Base64.getEncoder().encodeToString(secretBytes).toString();	
			log.info("Seceret Key >>  "+secretkey);
			//setSecretkey(secretkey);
			setSecretkey("test");
	}
	
	public static Date getFirstDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public String getAuthToken(String username) {
		log.info("Build auth token for  user >>" + username+ ". SecretBytes>>"+ getSecretkey());
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiryTime))
				.signWith(SignatureAlgorithm.HS512, getSecretkey()).compact();
	}

}
