package com.deeps.easycable.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.CustomerPaymentService;

@RestController
public class BillingController {

	@Autowired
	CustomerPaymentService customerPayment;

	@PostMapping("/operator/{operatorId}/billing")
	public ServiceResponse generateBilling(@PathVariable("operatorId") Long operatorId,
			@RequestParam(required = false)  @DateTimeFormat(pattern="yyyy-MM") Date billingMonth ) {
		return customerPayment.generateBilling(operatorId,billingMonth);
	}
}
