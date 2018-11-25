package com.deeps.easycable.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Operator implements Serializable {

	private static final long serialVersionUID = 7774563428562961990L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;	

	@Column(unique = true, nullable = false)
	private String name;

	private String subscriptionType;

	private int maxUser;

	@Column(nullable = false)
	private String subscriptionStatus;

	@Column(nullable = false)
	private long subscriptionCost;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public int getMaxUser() {
		return maxUser;
	}

	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}

	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public long getSubscriptionCost() {
		return subscriptionCost;
	}

	public void setSubscriptionCost(long subscriptionCost) {
		this.subscriptionCost = subscriptionCost;
	}

	public Operator() {
	}

	@Override
	public String toString() {
		return "Operator [id=" + id + ", name=" + name + ", subscriptionType=" + subscriptionType + ", maxUser="
				+ maxUser + ", subscriptionStatus=" + subscriptionStatus + ", subscriptionCost=" + subscriptionCost
				+ "]";
	}
}
