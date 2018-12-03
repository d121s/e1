package com.deeps.easycable.api.service;

import com.deeps.easycable.api.entity.CustomerPayment;
import com.deeps.easycable.api.request.CustomerPaymentRequest;

public interface CustomerPaymentService {
	
	public CustomerPayment addPayment(CustomerPaymentRequest request);
	
	public CustomerPayment updatePayment(CustomerPaymentRequest request, Long paymentId);
	
}
