package com.deeps.easycable.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.CustomerPaymentCollection;

@Repository
public interface CustomerPaymentCollectionRepo extends JpaRepository<CustomerPaymentCollection, Long> {

	// @Query(value="select sum(cp.subscription_cost-cp.payment_amt) as
	// pending_amt,cp.customer_id from customer_payment cp where cp.operator_id=?1
	// group by cp.customer_id")
	@Query(value = "select sum(cp.subscriptionCost-cp.paymentAmt) as pendingAmt,cp.customer.id from CustomerPayment cp where cp.operator.id=?1 group by cp.customer.id order by cp.customer.id asc")
	List<Object[]> findPaymentByOperatorId(Long operatorId);

	@Query(value = "select sum(cp.subscriptionCost-cp.paymentAmt) as pendingAmt from CustomerPayment cp where cp.customer.id=?1 group by cp.customer.id")
	Object findPaymentByCustomerId(Long customerId);

	@Query(value = "select sum(cp.subscriptionCost) as totalRevenue,sum(cp.paymentAmt) as totalPayment,cp.customer.id from CustomerPayment cp where cp.operator.id=?1 group by cp.customer.id order by cp.customer.id asc")
	List<Object[]> findPaymentReportByOperatorId(Long operatorId);

}