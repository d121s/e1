package com.deeps.easycable.api;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.google.common.collect.Lists;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String TERMS_OF_SERVICE = "Terms of service";
	public static final String CONTACT_NAME = "Easy Cable Support";
	public static final String CONTACT_URL = "Mail Address";
	public static final String CONTACT_EMAIL = "Mail ID";
	public static final String LICENSE_OF_API = "License of API";
	public static final String API_LICENSE_URL = "API license URL";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.deeps.easycable.api.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo()).groupName("EasyCable- API")
				.directModelSubstitute(LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class)
				.securitySchemes(Lists.newArrayList(apiKey())).securityContexts(Arrays.asList(securityContext()));
	}	

	private ApiInfo apiInfo() {
		return new ApiInfo("Easy Cable API SPECIFICATION", " API for Ezy Cables", "v1", TERMS_OF_SERVICE,
				new Contact(CONTACT_NAME, CONTACT_URL, CONTACT_EMAIL), LICENSE_OF_API, API_LICENSE_URL,
				Collections.emptyList());
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().scopeSeparator(",").additionalQueryStringParams(null)
				.useBasicAuthenticationWithAccessCodeGrant(false).build();
	}

	private ApiKey apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("apiKey", authorizationScopes));
	}

}