package com.deeps.easycable.api.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.entity.CustomerCollection;
import com.deeps.easycable.api.repo.CustomerPaymentCollectionRepo;
import com.deeps.easycable.api.request.PaymentStatus;
import com.deeps.easycable.api.response.CustomerColPymtResp;
import com.deeps.easycable.api.response.OperatorReportResponse;
import com.deeps.easycable.api.response.OperatorReportResponseDtls;
import com.deeps.easycable.api.service.CustomerPaymentService;
import com.deeps.easycable.api.service.ReportsService;

@Service
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	CustomerPaymentCollectionRepo custReport;

	@Autowired
	CustomerPaymentService custPay;

	@Override
	public OperatorReportResponse getOperatorPaymentReport(Long operatorId, String reportType) {

		if (reportType.equalsIgnoreCase("ALLTIME")) {
			return getOperatorAllTimePaymentReport(operatorId);
		} else if (reportType.equalsIgnoreCase("MONTHLY")) {
			return getOperatorMontlyPayment(operatorId);
		} else if (reportType.equalsIgnoreCase("YEARLY")) {
			return getOperatorYearlyPayment(operatorId);
		} else {
			return getOperatorAllTimePaymentReport(operatorId);
		}

	}

	public OperatorReportResponse getOperatorMontlyPayment(Long operatorId) {
		List<Object[]> paymentReportList = custReport.findPaymentReportByOperatorIdGroupedByBilling(operatorId);
		List<OperatorReportResponseDtls> reportDtlsList = new ArrayList<>();
		paymentReportList.forEach(x -> reportDtlsList.add(new OperatorReportResponseDtls(
				(double) x[0], (double) x[1],
				(double) x[0] - ((double) x[1] + (double) x[2]), 
				(double) x[2], 
				(long) x[3], 
				((Date) x[4]).toLocalDate())));
		return new OperatorReportResponse(0D, 0D, 0D, 0D, 0L, 0, 0, 0, reportDtlsList);
	}

	public OperatorReportResponse getOperatorYearlyPayment(Long operatorId) {
		List<Object[]> paymentReportList = custReport.findPaymentReportByOperatorIdGroupedByBillingYearly(operatorId);
		List<OperatorReportResponseDtls> reportDtlsList = new ArrayList<>();
		paymentReportList.forEach(x -> reportDtlsList.add(new OperatorReportResponseDtls(
				(double) x[0], (double) x[1],
				(double) x[0] - ((double) x[1] + (double) x[2]), 
				(double) x[2], 
				new Long((int)x[3]), 
				LocalDate.of((int)x[4], 1, 1))));
		return new OperatorReportResponse(0D, 0D, 0D, 0D, 0L, 0, 0, 0, reportDtlsList);
	}

	public OperatorReportResponse getOperatorAllTimePaymentReport(Long operatorId) {
		double totalRevenue = 0;
		double totalPaymentRecived = 0;
		double totalWriteOff = 0;
		final AtomicInteger paidCustomerCount = new AtomicInteger();
		final AtomicInteger noBillingCustomerCount = new AtomicInteger();
		final AtomicInteger notPaidCustomerCount = new AtomicInteger();
		List<Object[]> paymentReportList = custReport.findPaymentReportByOperatorIdGroupedByBilling(operatorId);
		for (Object[] payReport : paymentReportList) {
			totalRevenue = (double) payReport[0] + totalRevenue;
			totalPaymentRecived = (double) payReport[1] + totalPaymentRecived;
			totalWriteOff = (double) payReport[2] + totalPaymentRecived;
		}

		/*
		 * List<Object[]> paymentWriteOff =
		 * custReport.findPendingPaymentByOperatorIdGroupedByBilling(operatorId,
		 * PaymentStatus.WRITEOFF.name()); for (Object[] writeoff : paymentWriteOff) {
		 * totalWriteOff = (double) writeoff[0] + totalWriteOff; }
		 */

		CustomerColPymtResp custPaymentStatus = custPay.getPaymentsByOperator(operatorId, 1, 10000, null, null, null);
		for (CustomerCollection cust : custPaymentStatus.getCustomer()) {
			if (cust.getPaymentStatus().equalsIgnoreCase(PaymentStatus.PAID.name())) {
				paidCustomerCount.incrementAndGet();
			} else if (cust.getPaymentStatus().equalsIgnoreCase(PaymentStatus.NOBILLING.name())) {
				noBillingCustomerCount.incrementAndGet();
			} else if (cust.getPaymentStatus().equalsIgnoreCase(PaymentStatus.NOTPAID.name())) {
				notPaidCustomerCount.incrementAndGet();
			}
		}

		return new OperatorReportResponse(totalRevenue, totalRevenue - (totalPaymentRecived + totalWriteOff),
				totalPaymentRecived, totalWriteOff, custPaymentStatus.getTotalCount(), paidCustomerCount.get(),
				notPaidCustomerCount.get(), noBillingCustomerCount.get(), null);
	}

}
