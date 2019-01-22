package com.deeps.easycable.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints=@UniqueConstraint(columnNames= {"operator_id","customer_id","billingMonth"}))
public class CustomerPayment implements Serializable {

	private static final long serialVersionUID = 4006180211782400269L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "operator_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Operator operator;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private Customer customer;

	@Column(nullable = false)
	private Double subscriptionCost;

	@Column(nullable = false)
	private Double paymentAmt;

	@Column(nullable = false)
	private String paymentStatus;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)	
	@DateTimeFormat(pattern="yyyy-Mon")
	private Date billingMonth;

	@Column(nullable = true)
	private Date paymentDate;
}
