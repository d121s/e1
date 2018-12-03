package com.deeps.easycable.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.repo.CustomerRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.request.CustomerRequest;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepo custRepo;

	@Autowired
	OperatorRepo opRepo;

	@Autowired
	SubscriptionPackageRepo spRepo;

	public Customer setCustomer(CustomerRequest custRequest, Customer customer) {
		customer.setAadharNumber(custRequest.getAadharNumber());
		customer.setAddress(custRequest.getAddress());
		customer.setBoxId(custRequest.getBoxId());
		customer.setCardNumber(custRequest.getCardNumber());
		customer.setCode(custRequest.getCode());
		customer.setCustomerName(custRequest.getCustomerName());
		customer.setManufacturer(custRequest.getManufacturer());
		customer.setOperator(opRepo.findById(custRequest.getOperatorId()).get());
		customer.setPhoneNumber(custRequest.getPhoneNumber());
		customer.setStatus(custRequest.getStatus());
		customer.setPackageId(custRequest.getPackageId());
		customer.setZone(custRequest.getZone());
		return customer;

	}

	@Override
	public boolean isCustomerUnderLimit(long operatorId) {
		try {
			int customerCount=custRepo.findByOperatorId(operatorId).size();
			int subscriptionMaxCount=opRepo.findById(operatorId).getMaxUser();
			LOGGER.info("Current customer count >>"+customerCount);
			LOGGER.info("Customer Max in subscription >>"+subscriptionMaxCount);
			if ( customerCount < subscriptionMaxCount) {
				LOGGER.info("Remaining no of Customer Limit"+(subscriptionMaxCount-customerCount));
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public Customer addCustomer(CustomerRequest custRequest) {
		if (isCustomerUnderLimit(custRequest.getOperatorId())) {
			return custRepo.save(setCustomer(custRequest, new Customer()));
		} else {
			return null;
		}
	}

	public Customer updateCustomer(CustomerRequest custRequest, Long customerId) {
		return custRepo.save(setCustomer(custRequest, custRepo.findById(customerId).get()));
	}

	public ServiceResponse deleteCustomer(Long customerId) {
		custRepo.delete(custRepo.findById(customerId).get());
		return new ServiceResponse(new ResponseStatus(200, "Customer deleted sucessfully"));
	}

	public Customer getCustomerDetails(Long customerId) {
		return custRepo.findById(customerId).get();
	}

	public List<Customer> getCustomerList(Long operatorId, Long packageId) {
		if (operatorId == null && packageId != null) {
			return custRepo.findByPackageId(packageId);
			// return new ArrayList<Customer>();
		} else if (operatorId != null && packageId == null) {
			return custRepo.findByOperatorId(operatorId);
		} else if (operatorId != null && packageId != null) {
			return custRepo.findByOperatorIdAndPackageId(operatorId, packageId);
			// return new ArrayList<Customer>();
		} else {
			return new ArrayList<Customer>();
		}
	}
}
