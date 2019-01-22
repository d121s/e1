package com.deeps.easycable.api.service;

import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.request.CustomerRequest;
import com.deeps.easycable.api.request.CustomerSearchType;
import com.deeps.easycable.api.response.CustomerCollectionResponse;
import com.deeps.easycable.api.response.ServiceResponse;

public interface CustomerService {

	public Customer addCustomer(CustomerRequest custRequest);

	public Customer updateCustomer(CustomerRequest custRequest, Long customerId);

	public ServiceResponse deleteCustomer(Long customerId);

	public Customer getCustomerDetails(Long customerId, String qrCode);

	public CustomerCollectionResponse getCustomerList(Long operatorId, int pageNo,int pageSize,String searchValue,CustomerSearchType searchKey);
	
	boolean isCustomerUnderLimit(long operatorId);

}
