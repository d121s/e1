package com.deeps.easycable.api.response;

import java.util.List;

import com.deeps.easycable.api.entity.CustomerPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPaymentResponse {
	private long customerId;
	private Double pendingAmt;
	private String paymentStatus;
	private List<CustomerPayment> customerPayment;
}
