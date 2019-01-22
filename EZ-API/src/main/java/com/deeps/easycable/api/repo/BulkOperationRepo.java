package com.deeps.easycable.api.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.deeps.easycable.api.aop.LogMethodExecutionTime;
import com.deeps.easycable.api.entity.Customer;
import com.deeps.easycable.api.request.PaymentStatus;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class BulkOperationRepo {

	private final JdbcTemplate template;

	public BulkOperationRepo(JdbcTemplate template) {
		this.template = template;
	}

	@LogMethodExecutionTime
	@Transactional
	public void saveAllCustomer(List<Customer> customerList) {
		template.batchUpdate(
				"insert into customer (operator_id,customer_name,phone_number,status,qr_code,subscription_cost, "
						+ "address, box_id, card_number, zone, code, manufacturer , subscription_start_date) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						log.info("Customer create count >>" + i);
						ps.setLong(1,customerList.get(i).getOperator().getId());
						ps.setString(2, customerList.get(i).getCustomerName());
						ps.setString(3, customerList.get(i).getPhoneNumber());
						ps.setString(4, customerList.get(i).getStatus());
						ps.setString(5, customerList.get(i).getQrCode());
						ps.setDouble(6, customerList.get(i).getSubscriptionCost());
						ps.setString(7, customerList.get(i).getAddress());
						ps.setString(8, customerList.get(i).getBoxId());
						ps.setString(9, customerList.get(i).getCardNumber());
						ps.setString(10, customerList.get(i).getZone());
						ps.setString(11, customerList.get(i).getCode());
						ps.setString(12, customerList.get(i).getManufacturer());
						ps.setDate(13, customerList.get(i).getSubscriptionStartDate());
					}

					@Override
					public int getBatchSize() {
						return customerList.size();
					}
				});
	}

	@LogMethodExecutionTime
	@Transactional
	public void geneterCustomerBill(List<Customer> customerList,java.sql.Date billingMonth) {
		template.batchUpdate(
				"insert into customer_payment (payment_amt, payment_status, subscription_cost, billing_month, customer_id, operator_id) values (?, ?, ?, ?, ?, ?)",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						log.info("Bill genrated count >>" + i);
						ps.setDouble(1, 0);
						ps.setString(2, PaymentStatus.NOTPAID.name());
						ps.setDouble(3, customerList.get(i).getSubscriptionCost());
						ps.setDate(4, billingMonth);
						ps.setLong(5, customerList.get(i).getId());
						ps.setLong(6, customerList.get(i).getOperator().getId());
					}

					@Override
					public int getBatchSize() {
						return customerList.size();
					}
				});
	}
}