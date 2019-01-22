package com.deeps.easycable.api.response;

import java.util.List;

import com.deeps.easycable.api.entity.CustomerPayment;

import lombok.Data;

@Data
public class CustomerPaymentResponse {
	private long customerId;
	private Double paymentAmt;
	private String paymentStatus;
	private List<CustomerPayment> customerPayment;
}
