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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.request.CustomerRequest;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.CustomerService;

@RestController
public class CustomerController {

	private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

	@Autowired
	CustomerService custServices;

	@GetMapping("/customers")	
	public List<Customer> getCustomerList(@RequestParam(required = false) Long operatorId,@RequestParam(required = false) Long packageId) {
		LOGGER.debug("Get Request to view Customer Details");
		return custServices.getCustomerList(operatorId, packageId);
	}
	
	@GetMapping("/customers/{customerId}")
	// @PreAuthorize("hasAnyAuthority('admin')")
	public Customer getCustomer(@PathVariable("customerId") Long customerId) {
		LOGGER.debug("Get Request to view Customer Details");
		return custServices.getCustomerDetails(customerId);
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody CustomerRequest custRequest) {
		return custServices.addCustomer(custRequest);
	}
	
	@PutMapping("/customers/{customerId}")
	public Customer updateCustomer(@PathVariable("customerId") Long customerId,@RequestBody CustomerRequest custRequest) {
		return custServices.updateCustomer(custRequest, customerId);
	}
	

	@DeleteMapping("/customers/{customerId}")
	public ServiceResponse deleteCustomer(@PathVariable("customerId") Long customerId) {
		return custServices.deleteCustomer(customerId);
	}
}
