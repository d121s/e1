package com.deeps.easycable.api.request;

import java.util.List;

import lombok.Data;

@Data
public class SubscriptionPackageRequest {
	
	private String name;
	
	private Double cost;
	
	private Long operatorId;
	
	private List<Long> channelId;
}
