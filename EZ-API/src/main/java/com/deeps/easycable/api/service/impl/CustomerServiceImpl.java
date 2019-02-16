package com.deeps.easycable.api.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.entity.Channel;
import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.entity.CustomerCollection;
import com.deeps.easycable.api.entity.Operator;
import com.deeps.easycable.api.entity.SubscriptionPackage;
import com.deeps.easycable.api.exception.MandatoryDataMissingException;
import com.deeps.easycable.api.exception.NotFoundException;
import com.deeps.easycable.api.repo.ChannelRepo;
import com.deeps.easycable.api.repo.CustomerCollectionRepo;
import com.deeps.easycable.api.repo.CustomerRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.request.CustomerRequest;
import com.deeps.easycable.api.request.CustomerSearchType;
import com.deeps.easycable.api.response.CustomerCollectionResponse;
import com.deeps.easycable.api.response.CustomerResponse;
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

		if (custRequest.getOperatorId() == null) {
			throw new MandatoryDataMissingException("Operator Id cannot be null. please check");
		}

		Optional<Operator> updateOperator = opRepo.findById(custRequest.getOperatorId());

		if (!updateOperator.isPresent()) {
			throw new NotFoundException("Operator is missing for given Operator Id " + custRequest.getOperatorId());
		}

		customer.setAadharNumber(custRequest.getAadharNumber());
		customer.setAddress(custRequest.getAddress());
		customer.setBoxId(custRequest.getBoxId());
		customer.setCardNumber(custRequest.getCardNumber());
		customer.setCode(custRequest.getCode());
		customer.setCustomerName(custRequest.getCustomerName());
		customer.setManufacturer(custRequest.getManufacturer());
		customer.setOperator(updateOperator.get());
		customer.setPhoneNumber(custRequest.getPhoneNumber());
		customer.setStatus(custRequest.getStatus());
		customer.setZone(custRequest.getZone());
		customer.setChannel(channelRepo.findAllById(custRequest.getChannelId()));
		customer.setPackages(spRepo.findAllById(custRequest.getPackageId()));
		customer.setSubscriptionCost(custRequest.getSubscriptionCost());
		customer.setSubscriptionStartDate(
				custRequest.getSubscriptionStartDate() == null ? Date.valueOf(LocalDate.now().withDayOfMonth(1))
						: custRequest.getSubscriptionStartDate());
		return customer;
	}

	public CustomerResponse setCustomerResponse(Customer customer) {
		CustomerResponse custResp = new CustomerResponse();
		custResp.setAadharNumber(customer.getAadharNumber());
		custResp.setAddress(customer.getAddress());
		custResp.setBoxId(customer.getBoxId());
		custResp.setCardNumber(customer.getCardNumber());
		List<Long> channelList = new ArrayList<>();
		for (Channel chanl : customer.getChannel())
			channelList.add(chanl.getId());
		custResp.setChannelId(channelList);
		List<Long> packageList = new ArrayList<>();
		for (SubscriptionPackage pack : customer.getPackages())
			packageList.add(pack.getId());
		custResp.setPackageId(packageList);
		custResp.setCode(customer.getCode());
		custResp.setCustomerName(customer.getCustomerName());
		custResp.setManufacturer(customer.getManufacturer());
		custResp.setOperatorId(customer.getOperator().getId());
		custResp.setPhoneNumber(customer.getPhoneNumber());
		custResp.setStatus(customer.getStatus());
		custResp.setSubscriptionCost(customer.getSubscriptionCost());
		custResp.setSubscriptionStartDate(customer.getSubscriptionStartDate());
		custResp.setZone(customer.getZone());
		custResp.setId(customer.getId());
		return custResp;
	}

	@Override
	public boolean isCustomerUnderLimit(long operatorId) {
		try {
			int customerCount = custRepo.findByOperatorId(operatorId).size();
			int subscriptionMaxCount = opRepo.findById(operatorId).get().getMaxUser();
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
		log.info("Add customer with details"+custRequest);
		custRequest.setStatus("Active");
		if (isCustomerUnderLimit(custRequest.getOperatorId())) {
			return custRepo.save(setCustomer(custRequest, new Customer()));
		} else {
			return null;
		}
	}

	@Override
	public Customer updateCustomer(CustomerRequest custRequest, Long customerId) {
		log.info("Update customer with details"+custRequest);
		if (customerId == null) {
			throw new MandatoryDataMissingException("Customer Id cannot be null");
		}
		Optional<Customer> updateCustomer = custRepo.findById(customerId);

		if (!updateCustomer.isPresent()) {
			throw new NotFoundException("Customer Id is not valid " + customerId);
		}

		return custRepo.save(setCustomer(custRequest, updateCustomer.get()));
	}

	public ServiceResponse deleteCustomer(Long customerId) {
		custRepo.delete(custRepo.findById(customerId).get());
		return new ServiceResponse(new ResponseStatus(200, "Customer deleted sucessfully"));
	}

	public Customer getCustomerDetails(Long customerId, String qrCode) {
		if (null != customerId) {
			return custRepo.findById(customerId).get();
		} else if (null != qrCode) {
			return custRepo.findByQrCode(qrCode);
		}
		return new Customer();
	}
	
	/*
	public CustomerResponse getCustomerDetails(Long customerId, String qrCode) {
		if (null != customerId) {
			return setCustomerResponse(custRepo.findById(customerId).get());
		} else if (null != qrCode) {
			return setCustomerResponse(custRepo.findByQrCode(qrCode));
		}
		return new CustomerResponse();
	}*/

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
					cutomerPageContent = custCollectionRepo.findByOperatorIdAndCustomerNameContaining(operatorId,
							searchValue, pageable);
					break;
				case ADDRESS:
					cutomerPageContent = custCollectionRepo.findByOperatorIdAndAddressContaining(operatorId,
							searchValue, pageable);
					break;
				case ZONE:
					cutomerPageContent = custCollectionRepo.findByOperatorIdAndZoneContaining(operatorId, searchValue,
							pageable);
					break;
				case CODE:
					cutomerPageContent = custCollectionRepo.findByOperatorIdAndCodeContaining(operatorId, searchValue,
							pageable);
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
