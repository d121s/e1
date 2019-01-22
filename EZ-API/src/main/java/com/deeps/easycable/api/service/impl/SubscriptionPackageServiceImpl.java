package com.deeps.easycable.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deeps.easycable.api.entity.SubscriptionPackage;
import com.deeps.easycable.api.entity.SubscriptionPackageCollection;
import com.deeps.easycable.api.repo.ChannelRepo;
import com.deeps.easycable.api.repo.OperatorRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageCollectionRepo;
import com.deeps.easycable.api.repo.SubscriptionPackageRepo;
import com.deeps.easycable.api.request.SubscriptionPackageRequest;
import com.deeps.easycable.api.response.ResponseStatus;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.SubscriptionPackageServices;;

@Service
public class SubscriptionPackageServiceImpl implements SubscriptionPackageServices {

	@Autowired
	SubscriptionPackageRepo spRepo;
	
	@Autowired
	SubscriptionPackageCollectionRepo spCollectionRepo;
	
	@Autowired
	ChannelRepo chRepo;
	
	@Autowired
	OperatorRepo opRepo;
	
	public SubscriptionPackage setSubscriptionPackage(SubscriptionPackageRequest spRequest,SubscriptionPackage sp) {
		sp.setCost(spRequest.getCost());
		sp.setName(spRequest.getName());
		sp.setOperator(opRepo.findById(spRequest.getOperatorId()).get());
		sp.setChannel(chRepo.findAllById(spRequest.getChannelId()));
		return sp;
	}
	
	@Override
	public SubscriptionPackage getPackageDtls(Long subscriptionId){
		return spRepo.findById(subscriptionId).get();
	}
	
	@Override
	public List<SubscriptionPackageCollection> getPackageList(Long operatorId) {
		return spCollectionRepo.findByOperatorId(operatorId);
	}

	@Override
	public SubscriptionPackage addPackage(SubscriptionPackageRequest spRequest) {
		return spRepo.save(setSubscriptionPackage(spRequest, new SubscriptionPackage()));
	}
	
	@Override
	public SubscriptionPackage updatePackage(SubscriptionPackageRequest spRequest,Long subscriptionId) {
		return spRepo.save(setSubscriptionPackage(spRequest,spRepo.findById(subscriptionId).get()));
	}
	
	@Override
	public ServiceResponse deletePackage(Long spId) {
		spRepo.delete(spRepo.findById(spId).get());
		return new ServiceResponse(new ResponseStatus(200,"Subscription package Deleted Successfully"));		
	}
	

}
