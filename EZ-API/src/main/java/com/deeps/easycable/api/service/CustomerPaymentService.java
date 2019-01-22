package com.deeps.easycable.api.service;

import java.util.Date;
import java.util.List;

import com.deeps.easycable.api.entity.CustomerPayment;
import com.deeps.easycable.api.request.CustomerPaymentRequest;
import com.deeps.easycable.api.request.CustomerSearchType;
import com.deeps.easycable.api.response.CustomerColPymtResp;
import com.deeps.easycable.api.response.CustomerPaymentResponse;
import com.deeps.easycable.api.response.ServiceResponse;

public interface CustomerPaymentService {

	public List<CustomerPayment> updateCustomerPayment(CustomerPaymentRequest request, Long customerId);

	public CustomerPayment getPayment(Long paymentId);

	public CustomerColPymtResp getPaymentsByOperator(Long operatorId, int page, int pageSize,
			String searchValue, CustomerSearchType searchKey, boolean isNotPaid);

	public CustomerPayment updatePayment(CustomerPaymentRequest request, Long paymentId,boolean isWriteOff);

	public ServiceResponse generateBilling(Long operatorId, Date billingMonth);
	
	public CustomerPaymentResponse getPaymentsByCustomer(Long customerId);
}
