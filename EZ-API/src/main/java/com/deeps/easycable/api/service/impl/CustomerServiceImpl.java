package com.deeps.easycable.api.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.entity.CustomerCollection;
import com.deeps.easycable.api.repo.ChannelRepo;
import com.deeps.easycable.api.repo.CustomerCollectionRepo;
import com.deeps.easycable.api.repo.CustomerRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.request.CustomerRequest;
import com.deeps.easycable.api.request.CustomerSearchType;
import com.deeps.easycable.api.response.CustomerCollectionResponse;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.CustomerService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {	

	@Autowired
	CustomerRepo custRepo;
	
	@Autowired
	CustomerCollectionRepo custCollectionRepo;
	
	@Autowired
	OperatorRepo opRepo;

	@Autowired
	SubscriptionPackageRepo spRepo;
	
	@Autowired
	ChannelRepo channelRepo;

	public Customer setCustomer(CustomerRequest custRequest, Customer customer) {
		if (customer.getQrCode() == null) {
			customer.setQrCode(UUID.randomUUID().toString());
		}
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
		customer.setZone(custRequest.getZone());
		customer.setChannel(channelRepo.findAllById(custRequest.getChannelId()));
		customer.setPackages(spRepo.findAllById(custRequest.getPackageId()));
		customer.setSubscriptionCost(custRequest.getSubscriptionCost());
		return customer;

	}

	@Override
	public boolean isCustomerUnderLimit(long operatorId) {
		try {
			int customerCount = custRepo.findByOperatorId(operatorId).size();
			int subscriptionMaxCount = opRepo.findById(operatorId).getMaxUser();
			log.info("Current customer count >>" + customerCount);
			log.info("Customer Max in subscription >>" + subscriptionMaxCount);
			if (customerCount < subscriptionMaxCount) {
				log.info("Remaining no of Customer Limit" + (subscriptionMaxCount - customerCount));
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Customer addCustomer(CustomerRequest custRequest) {
		custRequest.setStatus("Active");
		if (isCustomerUnderLimit(custRequest.getOperatorId())) {
			return custRepo.save(setCustomer(custRequest, new Customer()));
		} else {
			return null;
		}
	}

	@Override
	public Customer updateCustomer(CustomerRequest custRequest, Long customerId) {
		return custRepo.save(setCustomer(custRequest, custRepo.findById(customerId).get()));
	}

	public ServiceResponse deleteCustomer(Long customerId) {
		custRepo.delete(custRepo.findById(customerId).get());
		return new ServiceResponse(new ResponseStatus(200, "Customer deleted sucessfully"));
	}

	public Customer getCustomerDetails(Long customerId, String qrCode) {
		if(null!=customerId) {
			return custRepo.findById(customerId).get();
		}else if(null!=qrCode){
			return custRepo.findByQrCode(qrCode);
		}
		return new Customer();
	}

	@Override
	public CustomerCollectionResponse getCustomerList(Long operatorId, int pageNo, int pageSize, String searchValue,
			CustomerSearchType searchKey) {
		if (operatorId != null) {
			Pageable pageable = PageRequest.of((pageNo - 1), pageSize, Direction.ASC, "customerName");
			Page<CustomerCollection> cutomerPageContent = null;

			if (searchKey == null) {
				cutomerPageContent = custCollectionRepo.findByOperatorId(operatorId, pageable);
			} else {
				switch (searchKey) {
				case NAME:
					cutomerPageContent = custCollectionRepo.findByOperatorIdAndCustomerNameContaining(operatorId, searchValue,
							pageable);
					break;
				case ADDRESS:
					cutomerPageContent = custCollectionRepo.findByOperatorIdAndAddressContaining(operatorId, searchValue,
							pageable);
					break;
				case ZONE:
					cutomerPageContent = custCollectionRepo.findByOperatorIdAndZoneContaining(operatorId, searchValue, pageable);
					break;
				case CODE:
					cutomerPageContent = custCollectionRepo.findByOperatorIdAndCodeContaining(operatorId, searchValue, pageable);
					break;
				default:
					cutomerPageContent = custCollectionRepo.findByOperatorId(operatorId, pageable);
				}
			}

			CustomerCollectionResponse cr = new CustomerCollectionResponse();
			cr.setCustomer(cutomerPageContent.getContent());
			cr.setHasMore(
					(cutomerPageContent.getTotalPages() > (cutomerPageContent.getPageable().getPageNumber() + 1)) ? true
							: false);
			cr.setPage(cutomerPageContent.getPageable().getPageNumber() + 1);
			cr.setPageSize(cutomerPageContent.getNumberOfElements());
			cr.setTotalCount(cutomerPageContent.getTotalElements());
			cr.setTotalPages(cutomerPageContent.getTotalPages());
			return cr;
		} else {
			return new CustomerCollectionResponse();
		}
	}
}
