package com.deeps.easycable.api.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.deeps.easycable.api.request.SubscriptionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table
@Data
public class Operator implements Serializable {

	private static final long serialVersionUID = 7774563428562961990L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;	

	@Column(unique = true,nullable=false)
	private String name;
	
	@Column(nullable =true)
	private String email;

	@Column(nullable =false)
	@JsonIgnore
	private String  password;
	
	private String operatorAgencyName;

	@Column(nullable=false)
	private SubscriptionType subscriptionType;

	private Integer maxUser;

	@Column(nullable = false)
	private String subscriptionStatus;

	@Column(nullable = false)
	private Double subscriptionCost;
	
	private Integer billingDay;	
	
	@OneToMany(mappedBy="operator")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Column(nullable=true)
	@JsonIgnore
	private List<Customer> customers;
	
	@OneToMany(mappedBy="operator")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Column(nullable=true)
	@JsonIgnore
	private List<SubscriptionPackage> packages;
	
}
