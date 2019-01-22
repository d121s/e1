package com.deeps.easycable.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.SubscriptionPackage;

@Repository
public interface SubscriptionPackageRepo extends JpaRepository<SubscriptionPackage, Long> {

	public SubscriptionPackage findById(String subscriptionId);
	
	public List<SubscriptionPackage> findByOperatorId(long operator_id);
	
	public SubscriptionPackage findByOperatorIdAndName(long operator_id,String name);

}
