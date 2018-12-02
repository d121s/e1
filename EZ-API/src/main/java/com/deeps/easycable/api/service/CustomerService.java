package com.deeps.easycable.api.service;

import java.util.List;

import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.request.CustomerRequest;
import com.deeps.easycable.api.response.ServiceResponse;

public interface CustomerService {

	public Customer addCustomer(CustomerRequest custRequest);

	public Customer updateCustomer(CustomerRequest custRequest, Long customerId);

	public ServiceResponse deleteCustomer(Long customerId);

	public Customer getCustomerDetails(Long customerId);

	public List<Customer> getCustomerList(Long operatorId, Long packageId);

}
