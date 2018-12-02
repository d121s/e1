package com.deeps.easycable.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CustomerPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "operator_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Operator operator;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Customer customer;

	private Date subscritionPaymentDate;

	private double subscriptionCost;

	private double paymentAmt;

	private String paymentStatus;

	private Date paymentDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getSubscritionPaymentDate() {
		return subscritionPaymentDate;
	}

	public void setSubscritionPaymentDate(Date subscritionPaymentDate) {
		this.subscritionPaymentDate = subscritionPaymentDate;
	}

	public double getSubscriptionCost() {
		return subscriptionCost;
	}

	public void setSubscriptionCost(double subscriptionCost) {
		this.subscriptionCost = subscriptionCost;
	}

	public double getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		return "CustomerPayment [id=" + id + ", operator=" + operator + ", customer=" + customer
				+ ", subscritionPaymentDate=" + subscritionPaymentDate + ", subscriptionCost=" + subscriptionCost
				+ ", paymentAmt=" + paymentAmt + ", paymentStatus=" + paymentStatus + ", paymentDate=" + paymentDate
				+ "]";
	}

	public CustomerPayment() {

	}

}
