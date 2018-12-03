package com.deeps.easycable.api.controller;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.entity.CustomerPayment;
import com.deeps.easycable.api.request.CustomerPaymentRequest;
import com.deeps.easycable.api.service.CustomerPaymentService;

@RestController
public class CustomerPaymentController {

	@Autowired
	CustomerPaymentService custPaymentService;

	private static final Logger LOGGER = LogManager.getLogger(CustomerPaymentController.class);

	@PostMapping("/customerPayments")
	public CustomerPayment addCustomerPayment(@RequestBody CustomerPaymentRequest custPayRequest) {
		LOGGER.info("Addding Customer Payment ");
		return custPaymentService.addPayment(custPayRequest);
	}

	@PutMapping("/customerPayments")
	public CustomerPayment updateCustomerPayment(@RequestBody CustomerPaymentRequest custPayRequest,
			@RequestParam Long paymentId) {
		LOGGER.info("Updating Customer Payment ");
		return custPaymentService.updatePayment(custPayRequest, paymentId);
	}

}
