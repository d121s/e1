package com.deeps.easycable.api.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.service.OperatorServices;

import lombok.extern.log4j.Log4j2;

import com.deeps.easycable.api.entity.Operator;
import com.deeps.easycable.api.request.OperatorRequest;
import com.deeps.easycable.api.response.ServiceResponse;

@RestController
@Log4j2
public class OperatorController {

	private static final Logger LOGGER = LogManager.getLogger(HealthCheckController.class);

	@Autowired
	OperatorServices oprServices;

	@GetMapping("/operators")
	// @PreAuthorize("hasAnyAuthority('admin')")
	public List<Operator> getOperatorList() {
		log.debug("Get Request to view Operator Details");
		return oprServices.getOperatorDetails();
	}
	
	@GetMapping("/operators/{operatorId}")
	// @PreAuthorize("hasAnyAuthority('admin')")
	public Operator getOperator(@PathVariable("operatorId") Long operatorId) {
		log.debug("Get Request to view Operator Details");
		return oprServices.getOperator(operatorId);
	}
	
	@PostMapping("/operators")
	public Operator addOperator(@RequestBody OperatorRequest oprRequest) {
		return oprServices.addOperator(oprRequest);
	}
	
	@PutMapping("/operators/{operatorId}")
	public Operator updateOperator(@PathVariable("operatorId") Long operatorId,@RequestBody OperatorRequest oprRequest) {
		return oprServices.updateOperator(oprRequest, operatorId);
	}
	

	@DeleteMapping("/operators/{operatorId}")
	public ServiceResponse deleteOperator(@PathVariable("operatorId") Long operatorId) {
		return oprServices.deleteOperator(operatorId);
	}
}
