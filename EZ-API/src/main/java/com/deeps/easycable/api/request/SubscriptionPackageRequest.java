package com.deeps.easycable.api.request;

public class SubscriptionPackageRequest {
	
	private String name;
	
	private Double cost;
	
	private long operatorId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	
	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public SubscriptionPackageRequest(String name, Double cost, long operatorId) {
		super();
		this.name = name;
		this.cost = cost;
		this.operatorId = operatorId;
	}

	@Override
	public String toString() {
		return "SubscriptionPackageRequest [name=" + name + ", cost=" + cost + ", operatorId=" + operatorId + "]";
	}

	public SubscriptionPackageRequest() {
	}	
	
	

}
