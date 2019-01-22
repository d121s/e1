package com.deeps.easycable.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.response.HealthStatus;

import lombok.extern.log4j.Log4j2;

import com.deeps.easycable.api.response.ComponentStatus;


@RestController
@Log4j2
public class HealthCheckController {
	
	@GetMapping("/status")	
	//@PreAuthorize("hasAnyAuthority('admin')")	
	public HealthStatus getStatus() {	
		log.debug("Get Request to view componenet Status");
		//telemetryClient.trackEvent("Initializing call to the Status Service");
		return new HealthStatus("OK",new ComponentStatus("OK","OK"));
	}
}
