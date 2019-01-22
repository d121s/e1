package com.deeps.easycable.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deeps.easycable.api.entity.SubscriptionPackageCollection;

@Repository
public interface SubscriptionPackageCollectionRepo extends JpaRepository<SubscriptionPackageCollection, Long> {

	public SubscriptionPackageCollection findById(String subscriptionId);
	
	public List<SubscriptionPackageCollection> findByOperatorId(long operator_id);
	
	public SubscriptionPackageCollection findByOperatorIdAndName(long operator_id,String name);

}
