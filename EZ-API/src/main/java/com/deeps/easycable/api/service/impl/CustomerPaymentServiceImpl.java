package com.deeps.easycable.api.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.ApiUtils;
import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.entity.CustomerCollection;
import com.deeps.easycable.api.entity.CustomerPayment;
import com.deeps.easycable.api.exception.CustomException;
import com.deeps.easycable.api.exception.MandatoryDataMissingException;
import com.deeps.easycable.api.repo.BulkOperationRepo;
import com.deeps.easycable.api.repo.CustomerPaymentCollectionRepo;
import com.deeps.easycable.api.repo.CustomerPaymentRepo;
import com.deeps.easycable.api.repo.CustomerRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.request.CustomerPaymentRequest;
import com.deeps.easycable.api.request.CustomerSearchType;
import com.deeps.easycable.api.request.PaymentStatus;
import com.deeps.easycable.api.response.CustomerColPymtResp;
import com.deeps.easycable.api.response.CustomerCollectionResponse;
import com.deeps.easycable.api.response.CustomerPaymentResponse;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.CustomerPaymentService;
import com.deeps.easycable.api.service.CustomerService;
import com.google.common.util.concurrent.AtomicDouble;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomerPaymentServiceImpl implements CustomerPaymentService {

	@Autowired
	CustomerRepo custRepo;

	@Autowired
	OperatorRepo opRepo;

	@Autowired
	SubscriptionPackageRepo spRepo;

	@Autowired
	CustomerPaymentRepo custPayRepo;

	@Autowired
	CustomerPaymentCollectionRepo custPayColRepo;

	@Autowired
	BulkOperationRepo billRepo;

	@Autowired
	CustomerService custService;

	public CustomerPayment setCustPayment(CustomerPaymentRequest request, CustomerPayment custPayment,
			boolean isWriteOff) {
		custPayment.setPaymentDate(new Date());
		if (request.getWriteOffAmt() > custPayment.getSubscriptionCost()) {
			throw new MandatoryDataMissingException("Write Off Ammount cannot be greater than Payment Ammount");
		}
		
		if(custPayment.getSubscriptionCost() < (request.getPaymentAmt()+request.getWriteOffAmt())) {
			throw new MandatoryDataMissingException(" Total WriteOff + Payment is greater than actual Subscription cost");
		}
		
		if (isWriteOff || custPayment.getPaymentStatus().equalsIgnoreCase(PaymentStatus.WRITEOFF.name())) {			
			custPayment.setPaymentStatus(PaymentStatus.WRITEOFF.name());
			custPayment.setWriteoffAmt(request.getWriteOffAmt());
		} else {
			custPayment.setPaymentStatus(getPaymentStatus(custPayment.getSubscriptionCost(), request.getPaymentAmt()));
		}
		custPayment.setPaymentAmt(request.getPaymentAmt());
		if (custPayment.getSubscriptionCost() - (custPayment.getPaymentAmt() + custPayment.getWriteoffAmt()) == 0) {
			custPayment.setPaymentStatus(PaymentStatus.PAID.name());
		}

		return custPayment;
	}

	public CustomerPayment generateBilling(Customer customer, CustomerPayment custPayment) {
		custPayment.setCustomer(customer);
		custPayment.setOperator(opRepo.getOne(customer.getOperator().getId()));
		custPayment.setPaymentAmt(new Double(0));
		custPayment.setWriteoffAmt(new Double(0));
		custPayment.setPaymentDate(null);
		custPayment.setSubscriptionCost(customer.getSubscriptionCost());
		custPayment.setBillingMonth(ApiUtils.getFirstDateOfCurrentMonth());
		custPayment.setPaymentStatus(PaymentStatus.NOTPAID.name());
		// log.info("Customer Payment >>"+custPayment);
		return custPayment;
	}

	public String getPaymentStatus(Double subscriptionCost, Double paymentCost) {
		Double remainingAmt = subscriptionCost - paymentCost;
		if (paymentCost == 0) {
			return PaymentStatus.NOTPAID.name();
		} else if (remainingAmt > 0) {
			return PaymentStatus.PARTIAL.name();
		} else {
			return PaymentStatus.PAID.name();
		}
	}

	@Override
	public CustomerPayment updatePayment(CustomerPaymentRequest request, Long paymentId, boolean isWriteOff) {
		return custPayRepo.save(setCustPayment(request, custPayRepo.getOne(paymentId), isWriteOff));
	}

	public ServiceResponse generateBilling(Long operatorId, Date billingMonth) {
		List<Customer> customerList = custRepo.findByOperatorId(operatorId);
		log.info("Billing to be genrated for operator, with Customer count >>" + customerList.size());

		billRepo.geneterCustomerBill(customerList, billingMonth != null ? new java.sql.Date(billingMonth.getTime())
				: java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1)));
		/*
		 * List<CustomerPayment> customerPayment=new ArrayList<CustomerPayment>();
		 * customerList.forEach(cust -> customerPayment.add(generateBilling(cust, new
		 * CustomerPayment()))); custPayRepo.saveAll(customerPayment);
		 */
		return new ServiceResponse(
				new ResponseStatus(200, "Bill Generated for all --" + customerList.size() + " successfully"));
	}

	@Override
	public CustomerPaymentResponse updateCustomerPayment(CustomerPaymentRequest request, Long customerId) {
		Double totalPayment = request.getPaymentAmt();
		Double paymentAmt = null;
		double pendingAmt = 0;
		List<CustomerPayment> paymentList = custPayRepo.findByCustomerIdAndPaymentStatusInOrderByBillingMonthAsc(
				customerId, Arrays.asList(PaymentStatus.NOTPAID.name(), PaymentStatus.PARTIAL.name(),
						PaymentStatus.WRITEOFF.name()));

		pendingAmt = paymentList.stream().collect(Collectors.summingDouble(
				payment -> payment.getSubscriptionCost() - (payment.getPaymentAmt() + payment.getWriteoffAmt())));

		if (totalPayment > pendingAmt) {
			throw new CustomException(
					"Payment amount " + totalPayment + " is greater than pending Amount " + pendingAmt);
		}

		for (CustomerPayment custPayment : paymentList) {
			paymentAmt = custPayment.getSubscriptionCost()
					- (custPayment.getPaymentAmt() + custPayment.getWriteoffAmt());
			custPayment.setPaymentDate(null != request.getPaymentDate() ? request.getPaymentDate() : new Date());
			if (totalPayment >= paymentAmt) {
				custPayment.setPaymentAmt(custPayment.getPaymentAmt() + paymentAmt);
				custPayment.setPaymentStatus(PaymentStatus.PAID.name());
				custPayRepo.save(custPayment);
			} else {
				custPayment.setPaymentAmt(custPayment.getPaymentAmt() + totalPayment);
				custPayment
						.setPaymentStatus(custPayment.getPaymentStatus().equalsIgnoreCase(PaymentStatus.WRITEOFF.name())
								? PaymentStatus.WRITEOFF.name()
								: PaymentStatus.PARTIAL.name());
				custPayRepo.save(custPayment);
				break;
			}
			totalPayment = totalPayment - paymentAmt;

		}
		return getPaymentsByCustomer(customerId);
	}

	@Override
	public CustomerPaymentResponse getPaymentsByCustomer(Long customerId) {
		List<CustomerPayment> paymentList = custPayRepo.findByCustomerIdOrderByBillingMonthDesc(customerId);
		AtomicDouble paymentAmt = new AtomicDouble();
		paymentList.forEach(cust -> paymentAmt
				.set(cust.getSubscriptionCost() - (cust.getWriteoffAmt() + cust.getPaymentAmt()) + paymentAmt.get()));
		log.info("Pending payment ammount >>" + paymentAmt);
		CustomerPaymentResponse custResponse = new CustomerPaymentResponse();
		custResponse.setCustomerId(customerId);
		custResponse.setCustomerPayment(paymentList);
		custResponse.setPendingAmt(paymentAmt.get());
		custResponse.setPaymentStatus(paymentAmt.get() > 0 ? PaymentStatus.NOTPAID.name() : PaymentStatus.PAID.name());
		return custResponse;
	}

	@Override
	public CustomerPayment getPayment(Long paymentId) {
		return custPayRepo.findById(paymentId).get();
	}

	@Override
	public CustomerColPymtResp getPaymentsByOperator(Long operatorId, int pageNo, int pageSize, String searchValue,
			CustomerSearchType searchKey, Boolean isNotPaid) {
		CustomerCollectionResponse custList = custService.getCustomerList(operatorId, pageNo, pageSize, searchValue,
				searchKey);
		List<Object[]> paymentList = custPayColRepo.findPaymentByOperatorId(operatorId);

		log.info("TotalPaymentFound" + paymentList.size());
		paymentList.forEach(payment -> log.info(payment[0] + ">>>" + payment[1]));
		CustomerColPymtResp custPayResp = new CustomerColPymtResp();
		List<CustomerCollection> customerList = setCustomerPayment(custList.getCustomer(), paymentList, isNotPaid);
		custPayResp.setCustomer(customerList);
		custPayResp.setHasMore(custList.isHasMore());
		custPayResp.setPage(custList.getPage());
		custPayResp.setPageSize(customerList.size());
		custPayResp.setTotalCount(custList.getTotalCount());
		custPayResp.setTotalPages(custList.getTotalPages());
		return custPayResp;
	}

	public List<CustomerCollection> setCustomerPayment(List<CustomerCollection> custList, List<Object[]> paymentList,
			Boolean isNotPaid) {
		List<CustomerCollection> customerList = new ArrayList<>();
		for (CustomerCollection cust : custList) {
			Object[] custPayment = paymentList.stream().filter(pay -> cust.getId() == (long) pay[3]).findAny()
					.orElse(null);

			if (custPayment != null) {
				double pendingAmt = (Double) custPayment[0] - ((Double) custPayment[1] + (Double) custPayment[2]);
				cust.setPendingPaymentAmt(pendingAmt);
				String payStatus = pendingAmt > 0 ? PaymentStatus.NOTPAID.name() : PaymentStatus.PAID.name();
				cust.setPaymentStatus(payStatus);
			} else {
				cust.setPendingPaymentAmt(new Double(0));
				cust.setPaymentStatus(PaymentStatus.NOBILLING.name());
				log.info("No Payment info found for Customer >>" + cust.getCustomerName());
			}
			customerList.add(cust);
		}
		if (null != isNotPaid) {
			if (isNotPaid) {
				customerList.removeIf(cust -> cust.getPaymentStatus().equalsIgnoreCase(PaymentStatus.PAID.name()));
				customerList.removeIf(cust -> cust.getPaymentStatus().equalsIgnoreCase(PaymentStatus.NOBILLING.name()));
			} else {
				customerList.removeIf(cust -> cust.getPaymentStatus().equalsIgnoreCase(PaymentStatus.NOTPAID.name()));
			}
		}
		return customerList;
	}
}
