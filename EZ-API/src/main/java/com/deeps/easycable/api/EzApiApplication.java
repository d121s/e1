package com.deeps.easycable.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EzApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzApiApplication.class, args);
	}
}
