package com.deeps.easycable.api.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperatorReportResponseDtls {
	
	private double expectedPayment;
	
	private double actualPayment;
	
	private double remainingPayment;
	
	private double totalWriteOFF;
	
	private long totalBillings;
	
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM")
	private LocalDate billingMonth;
}
