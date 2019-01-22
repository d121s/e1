package com.deeps.easycable.api.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table
@Data
public class Customer implements Serializable {

	private static final long serialVersionUID = 7774563428562961990L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Nationalized
	@Column(unique = false, nullable = false)
	private String customerName;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "operator_id", nullable = false)	
	private Operator operator;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id", nullable = true)
	private List<SubscriptionPackage> packages;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel_id", nullable = true)
	private List<Channel> channel;

	private String boxId;

	private String cardNumber;

	private String manufacturer;

	private String phoneNumber;

	private String aadharNumber;
	
	private double subscriptionCost;
	
	private Date subscriptionStartDate;

	@Nationalized
	private String zone;

	@Nationalized
	private String address;

	@Nationalized
	private String code;

	private String status;

	@Column(unique = true, nullable = false)
	private String qrCode;
}
