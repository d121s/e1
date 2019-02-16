package com.deeps.easycable.api.request;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerPaymentRequest {

	private double paymentAmt;
	
	private double writeOffAmt;

	private Date paymentDate;
}
