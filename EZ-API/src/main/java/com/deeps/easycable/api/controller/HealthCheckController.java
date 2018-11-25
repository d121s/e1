package com.deeps.easycable.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.response.HealthStatus;
import com.deeps.easycable.api.response.ComponentStatus;


@RestController
public class HealthCheckController {
	private static final Logger LOGGER = LogManager.getLogger(HealthCheckController.class);

	@GetMapping("/status")	
	//@PreAuthorize("hasAnyAuthority('admin')")	
	public HealthStatus getStatus() {	
		LOGGER.debug("Get Request to view componenet Status");
		//telemetryClient.trackEvent("Initializing call to the Status Service");
		return new HealthStatus("OK",new ComponentStatus("OK","OK"));
	}
}
