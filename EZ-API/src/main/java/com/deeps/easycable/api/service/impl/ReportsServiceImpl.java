package com.deeps.easycable.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.repo.CustomerPaymentCollectionRepo;
import com.deeps.easycable.api.response.OperatorReportResponse;
import com.deeps.easycable.api.service.ReportsService;

@Service
public class ReportsServiceImpl implements ReportsService {
	
	@Autowired
	CustomerPaymentCollectionRepo custColRepo;
	
	@Override
	public OperatorReportResponse getOperatorPaymentReport(Long operatorId) {
		double totalRevenue=0;
		double totalPaymentRecived=0;
		List<Object[]> paymentReportList=custColRepo.findPaymentReportByOperatorId(operatorId);
		for(Object[] payReport:paymentReportList) {
			totalRevenue=(double)payReport[0]+totalRevenue;
			totalPaymentRecived=(double)payReport[1]+totalPaymentRecived;
		}
		return new OperatorReportResponse(totalRevenue,totalRevenue-totalPaymentRecived,totalPaymentRecived,null,paymentReportList.size(),0,0);
	}

}
