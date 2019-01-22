package com.deeps.easycable.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.CustomerPayment;

@Repository
public interface CustomerPaymentRepo extends JpaRepository<CustomerPayment, Long> {
	
	List<CustomerPayment> findByCustomerIdAndPaymentStatusInOrderByBillingMonthAsc(Long customerId,List<String> paymentStatus);
	
	List<CustomerPayment> findByCustomerIdOrderByBillingMonthDesc(Long customerId);	
}