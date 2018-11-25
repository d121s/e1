package com.deeps.easycable.api.request;

public class PackageRequest {
	
	private String name;
	
	private long cost;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public PackageRequest(String name, long cost) {
		super();
		this.name = name;
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "PackageRequest [name=" + name + ", cost=" + cost + "]";
	}	

}
