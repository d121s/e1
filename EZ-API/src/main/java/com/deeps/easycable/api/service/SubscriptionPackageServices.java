package com.deeps.easycable.api.service;

import java.util.List;

import com.deeps.easycable.api.entity.SubscriptionPackage;
import com.deeps.easycable.api.entity.SubscriptionPackageCollection;
import com.deeps.easycable.api.request.SubscriptionPackageRequest;
import com.deeps.easycable.api.response.ServiceResponse;

public interface SubscriptionPackageServices {
	
	public SubscriptionPackage setSubscriptionPackage(SubscriptionPackageRequest spRequest,SubscriptionPackage sp);
	
	public SubscriptionPackage getPackageDtls(Long subscriptionId);
	
	public List<SubscriptionPackageCollection> getPackageList(Long operatorId);

	public SubscriptionPackage addPackage(SubscriptionPackageRequest spRequest);
	
	public SubscriptionPackage updatePackage(SubscriptionPackageRequest spRequest,Long subscriptionId);
	
	public ServiceResponse deletePackage(Long spId);	
}
