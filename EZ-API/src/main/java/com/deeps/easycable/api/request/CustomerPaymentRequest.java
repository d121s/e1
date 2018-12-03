package com.deeps.easycable.api.request;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CustomerPaymentRequest {

	private Long operatorId;

	private Long customerId;
	
	private double paymentAmt;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date subscriptionMonth;

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public double getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public Date getSubscriptionMonth() {
		return subscriptionMonth;
	}

	public void setSubscriptionMonth(Date subscriptionMonth) {
		this.subscriptionMonth = subscriptionMonth;
	}

	@Override
	public String toString() {
		return "CustomerPaymentRequest [operatorId=" + operatorId + ", customerId=" + customerId + ", paymentAmt="
				+ paymentAmt + ", subscriptionMonth=" + subscriptionMonth + "]";
	}	
}
