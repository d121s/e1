package com.deeps.easycable.api.request;

public class OperatorRequest {
	private String name;
	private String subscriptionType;
	private int maxUser;
	private String subscriptionStatus;
	private long subscriptionCost;

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

	public OperatorRequest() {

	}

	@Override
	public String toString() {
		return "OperatorRequest [name=" + name + ", subscriptionType=" + subscriptionType + ", maxUser=" + maxUser
				+ ", subscriptionStatus=" + subscriptionStatus + ", subscriptionCost=" + subscriptionCost + "]";
	}	
}
