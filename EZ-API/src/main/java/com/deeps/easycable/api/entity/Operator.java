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

	@Column(unique = false)
	private String name;
	
	@Column(unique = true, nullable =true)
	private String email;

	private String  password;

	private String subscriptionType;

	private Integer maxUser;

	@Column(nullable = false)
	private String subscriptionStatus;

	@Column(nullable = false)
	private Double subscriptionCost;
	
	private int billingDay;
	
	
	@OneToMany(mappedBy="operator")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<Customer> customers;
	
	@OneToMany(mappedBy="operator")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private List<SubscriptionPackage> packages;
	
}
