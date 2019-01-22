package com.deeps.easycable.api.request;

import java.util.List;

import lombok.Data;

@Data
public class CustomerRequest {

	private String customerName;

	private Long operatorId;

	private List<Long> packageId;
	
	private List<Long> channelId;
	
	private double subscriptionCost;

	private String boxId;

	private String cardNumber;

	private String manufacturer;

	private String phoneNumber;

	private String AadharNumber;

	private String Zone;

	private String Address;

	private String Code;

	private String status;
}
