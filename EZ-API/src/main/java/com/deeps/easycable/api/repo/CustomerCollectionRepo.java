package com.deeps.easycable.api.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.CustomerCollection;

@Repository
public interface CustomerCollectionRepo extends JpaRepository<CustomerCollection,Long>{

	public CustomerCollection findByQrCode(String qrCode);
	
	public List<CustomerCollection> findByOperatorId(long operatorId);
	
	Page<CustomerCollection> findByOperatorId(long operatorId,Pageable pageable);
	
	Page<CustomerCollection> findByOperatorIdAndCustomerNameContaining(long operatorId,String name,Pageable pageable);
	
	Page<CustomerCollection> findByOperatorIdAndZoneContaining(long operatorId,String zone,Pageable pageable);
	
	Page<CustomerCollection> findByOperatorIdAndCodeContaining(long operatorId,String code,Pageable pageable);
	
	Page<CustomerCollection> findByOperatorIdAndAddressContaining(long operatorId,String address,Pageable pageable);
	
	
}
