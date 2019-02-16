package com.deeps.easycable.api.service;

import com.deeps.easycable.api.response.OperatorReportResponse;

public interface ReportsService {
	
	public OperatorReportResponse getOperatorPaymentReport(Long operatorId,String reportType);
}
