package com.deeps.easycable.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deeps.easycable.api.entity.CustomerPayment;

public interface CustomerPaymentRepo extends JpaRepository<CustomerPayment, Long> {

}
