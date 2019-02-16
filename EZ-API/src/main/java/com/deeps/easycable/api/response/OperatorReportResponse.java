package com.deeps.easycable.api.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperatorReportResponse {

	private Double totalRevenue;
	private Double paymentPending;
	private Double paymentRecived;
	private Double paymentWritenOff;
	private long customerTotal;
	private int customerPaid;
	private int customerNotPaid;
	private int customerNoBilling;
	private List<OperatorReportResponseDtls> operatorReportResponseDtls;
}
