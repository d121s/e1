package com.deeps.easycable.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.entity.CustomerPayment;
import com.deeps.easycable.api.request.CustomerPaymentRequest;
import com.deeps.easycable.api.request.CustomerSearchType;
import com.deeps.easycable.api.response.CustomerColPymtResp;
import com.deeps.easycable.api.response.CustomerPaymentResponse;
import com.deeps.easycable.api.service.CustomerPaymentService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class CustomerPaymentController {

	@Autowired
	CustomerPaymentService custPaymentService;

	@PutMapping("/payment/{paymentId}")
	public CustomerPayment updatePayment(@PathVariable(required = true) Long paymentId,
			@RequestBody CustomerPaymentRequest custPayRequest,
			@RequestParam (defaultValue="false") boolean isWriteOff) {
		log.info("update Payment Details ");
		return custPaymentService.updatePayment(custPayRequest, paymentId,isWriteOff);
	}

	@PutMapping("/customer/{customerId}/payment")
	public CustomerPaymentResponse updateCustomerPayment(@PathVariable(required = true) Long customerId,
			@RequestBody CustomerPaymentRequest custPayRequest) {
		log.info("Addding Customer Payment ");
		return custPaymentService.updateCustomerPayment(custPayRequest, customerId);
	}

	@GetMapping("/customer/{customerId}/payment")
	public CustomerPaymentResponse getPaymentsByCustomer(@PathVariable(required = true) Long customerId) {
		log.info("Addding Customer Payment ");
		return custPaymentService.getPaymentsByCustomer(customerId);
	}

	@GetMapping("/payment/{paymentId}")
	public CustomerPayment getPayment(@PathVariable(required = true) Long paymentId) {
		log.info("Addding Customer Payment ");
		return custPaymentService.getPayment(paymentId);
	}

	@GetMapping("/operator/{operatorId}/payment")
	public CustomerColPymtResp getPaymentsByOperator(@RequestParam(required = true) Long operatorId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(required = false) String searchValue,
			@RequestParam(required = false) CustomerSearchType searchKey,
			@RequestParam(defaultValue = "true") boolean isNotPaid) {
		log.debug("Get Request to view Customer Details with Payments");
		return custPaymentService.getPaymentsByOperator(operatorId, page, pageSize, searchValue, searchKey, isNotPaid);
	}
}
