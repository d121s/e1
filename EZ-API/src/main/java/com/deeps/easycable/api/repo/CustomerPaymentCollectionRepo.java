package com.deeps.easycable.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.CustomerPaymentCollection;

@Repository
public interface CustomerPaymentCollectionRepo extends JpaRepository<CustomerPaymentCollection, Long> {

	@Query(value = "select sum(cp.subscriptionCost),sum(cp.paymentAmt),sum(cp.writeoffAmt),cp.customer.id from CustomerPayment cp where cp.operator.id=?1 group by cp.customer.id order by cp.customer.id asc")
	List<Object[]> findPaymentByOperatorId(Long operatorId);

	@Query(value = "select sum(cp.subscriptionCost),sum(cp.paymentAmt),sum(cp.writeoffAmt) from CustomerPayment cp where cp.customer.id=?1 group by cp.customer.id")
	Object[] findPaymentByCustomerId(Long customerId);

	/* DashBoard Query */
	@Query(value = "select sum(cp.subscriptionCost) as totalRevenue,sum(cp.paymentAmt) as totalPayment,sum(cp.writeoffAmt) as totalWriteOff,count(cp.id) as billingCount,cp.billingMonth from CustomerPayment cp where cp.operator.id=?1 group by cp.billingMonth order by cp.billingMonth desc ")
	List<Object[]> findPaymentReportByOperatorIdGroupedByBilling(Long operatorId);
	
	@Query(nativeQuery=true,value = "select sum(cp.subscription_Cost) as totalRevenue,sum(cp.payment_Amt) as totalPayment,sum(cp.writeoff_amt) as totalWriteOff,count(cp.id) as billingCount,\r\n" + 
			"datepart(yyyy, cp.billing_month) as billingYear\r\n" + 
			"from Customer_Payment cp where cp.operator_id=?1 group by datepart(yyyy, cp.billing_month) order by billingYear desc")
	List<Object[]> findPaymentReportByOperatorIdGroupedByBillingYearly(Long operatorId);

	@Query(value = "select sum(cp.subscriptionCost-cp.paymentAmt) as pendingAmt,count(cp.id) as billingCount,cp.billingMonth from CustomerPayment cp where cp.operator.id=?1 and paymentStatus=?2 group by cp.billingMonth order by cp.billingMonth desc ")
	List<Object[]> findPendingPaymentByOperatorIdGroupedByBilling(Long operatorId, String paymentStatus);

}