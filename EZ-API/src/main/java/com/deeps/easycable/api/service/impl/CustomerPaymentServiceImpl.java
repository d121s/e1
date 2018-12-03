package com.deeps.easycable.api.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.entity.CustomerPayment;
import com.deeps.easycable.api.repo.CustomerPaymentRepo;
import com.deeps.easycable.api.repo.CustomerRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.request.CustomerPaymentRequest;
import com.deeps.easycable.api.service.CustomerPaymentService;

@Service
public class CustomerPaymentServiceImpl implements CustomerPaymentService{

	@Autowired
	CustomerRepo custRepo;

	@Autowired
	OperatorRepo opRepo;

	@Autowired
	SubscriptionPackageRepo spRepo;

	@Autowired
	CustomerPaymentRepo custPayRepo;

	public CustomerPayment setCustPayment(CustomerPaymentRequest request, CustomerPayment custPayment) {
		Customer cust = custRepo.getOne(request.getCustomerId());
		custPayment.setCustomer(cust);
		custPayment.setOperator(opRepo.getOne(request.getOperatorId()));
		custPayment.setPaymentAmt(request.getPaymentAmt());
		custPayment.setPaymentDate(new Date());
		custPayment.setSubscriptionCost(spRepo.getOne(cust.getPackageId()).getCost());
		custPayment.setSubscriptionMonth(request.getSubscriptionMonth());
		custPayment.setPaymentStatus(
				getPaymentStatus(spRepo.getOne(cust.getPackageId()).getCost(), request.getPaymentAmt()));
		return custPayment;
	}

	public String getPaymentStatus(Double subscriptionCost, Double paymentCost) {
		Double remainingAmt = subscriptionCost - paymentCost;
		if (remainingAmt > 0) {
			return "PARTIALPAYAMENT";
		} else if (paymentCost == 0) {
			return "NO-PAYMENT";
		} else {
			return "PAID";
		}
	}
	
	@Override
	public CustomerPayment addPayment(CustomerPaymentRequest request) {
		return custPayRepo.save(setCustPayment(request, new CustomerPayment()));
	}

	@Override
	public CustomerPayment updatePayment(CustomerPaymentRequest request, Long paymentId) {
		return custPayRepo.save(setCustPayment(request, custPayRepo.getOne(paymentId)));
	}

}
