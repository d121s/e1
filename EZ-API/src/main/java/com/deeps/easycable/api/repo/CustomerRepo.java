package com.deeps.easycable.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deeps.easycable.api.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer,Long>{

	public Customer findById(long id);
	
	public List<Customer> findByPackageId(long packageId);
	
	public List<Customer> findByOperatorId(long operatorId);
	
	public List<Customer> findByOperatorIdAndPackageId(long operatorId,long packageId);
}
