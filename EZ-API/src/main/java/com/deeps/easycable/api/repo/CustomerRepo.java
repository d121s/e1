package com.deeps.easycable.api.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long>{

	public Customer findByQrCode(String qrCode);
	
	public List<Customer> findByOperatorId(long operatorId);
	
	Page<Customer> findByOperatorId(long operatorId,Pageable pageable);
	
	Page<Customer> findByOperatorIdAndCustomerNameContaining(long operatorId,String name,Pageable pageable);
	
	Page<Customer> findByOperatorIdAndZoneContaining(long operatorId,String zone,Pageable pageable);
	
	Page<Customer> findByOperatorIdAndCodeContaining(long operatorId,String code,Pageable pageable);
	
	Page<Customer> findByOperatorIdAndAddressContaining(long operatorId,String address,Pageable pageable);
	
	
}
