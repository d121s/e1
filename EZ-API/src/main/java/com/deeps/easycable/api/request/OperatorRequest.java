package com.deeps.easycable.api.request;

import lombok.Data;

@Data
public class OperatorRequest {
	private String emailId;
	private String name;
	private String password;
	private SubscriptionType subscriptionType;	
	private Integer maxUser;
	private String subscriptionStatus;
	private String operatorAgencyName;
	private Double subscriptionCost;
	private Integer billingDate;
}
