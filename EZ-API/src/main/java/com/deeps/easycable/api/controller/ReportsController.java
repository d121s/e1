package com.deeps.easycable.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.response.OperatorReportResponse;
import com.deeps.easycable.api.service.ReportsService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class ReportsController {
	
	@Autowired
	ReportsService reportsService;

	@GetMapping("/reports/{operatorId}")
	public OperatorReportResponse getReportByOperator(@PathVariable(required = true) Long operatorId) {
		log.info("Generating Operator Report ");
		return reportsService.getOperatorPaymentReport(operatorId);
	}

}
