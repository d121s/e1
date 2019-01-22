package com.deeps.easycable.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperatorReportResponse {

	private Double totalRevenue;
	private Double paymentPending;
	private Double paymentRecived;
	private Double paymentWritenOff;
	private int customerTotal;
	private int customerPaid;
	private int customerNotPaid;
}
