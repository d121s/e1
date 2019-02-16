package com.deeps.easycable.api.controller;

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
import com.deeps.easycable.api.request.CustomerSearchType;
import com.deeps.easycable.api.response.CustomerCollectionResponse;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.CustomerService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class CustomerController {

	@Autowired
	CustomerService custServices;

	@GetMapping("/customers")
	public CustomerCollectionResponse getCustomerList(@RequestParam(required = true) Long operatorId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(required = false) String searchValue,
			@RequestParam(required = false) CustomerSearchType searchKey) {
		log.debug("Get Request to view Customer Details");
		return custServices.getCustomerList(operatorId, page, pageSize, searchValue, searchKey);
	}

	@GetMapping("/customers/{customerId}")
	// @PreAuthorize("hasAnyAuthority('admin')")
	public Customer getCustomer(@PathVariable(required = false) Long customerId,
			@PathVariable(required = false) String qrCode) {
		log.debug("Get Request to view Customer Details");
		return custServices.getCustomerDetails(customerId, qrCode);
	}

	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody CustomerRequest custRequest) {
		log.info("Request to add new Customer");
		return custServices.addCustomer(custRequest);
	}

	@PutMapping("/customers/{customerId}")
	public Customer updateCustomer(@PathVariable("customerId") Long customerId,
			@RequestBody CustomerRequest custRequest) {
		return custServices.updateCustomer(custRequest, customerId);
	}

	@DeleteMapping("/customers/{customerId}")
	public ServiceResponse deleteCustomer(@PathVariable("customerId") Long customerId) {
		return custServices.deleteCustomer(customerId);
	}
}
