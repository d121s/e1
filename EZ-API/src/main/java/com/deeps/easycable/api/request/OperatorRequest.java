package com.deeps.easycable.api.request;

import lombok.Data;

@Data
public class OperatorRequest {
	private String name;
	private String subscriptionType;
	private int maxUser;
	private String subscriptionStatus;
	private Double subscriptionCost;
}
