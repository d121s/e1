package com.deeps.easycable.api.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
	
	private Long Id;
	
	private String customerName;

	private Long operatorId;

	private List<Long> packageId;
	
	private List<Long> channelId;
	
	private double subscriptionCost;
	
	private Date subscriptionStartDate;

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
