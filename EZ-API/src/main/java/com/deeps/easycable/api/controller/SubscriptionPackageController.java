package com.deeps.easycable.api.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deeps.easycable.api.entity.SubscriptionPackage;
import com.deeps.easycable.api.request.SubscriptionPackageRequest;
import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.SubscriptionPackageServices;

@RestController
public class SubscriptionPackageController {
	
	private static final Logger LOGGER = LogManager.getLogger(SubscriptionPackageController.class);
	
	@Autowired
	SubscriptionPackageServices spServices;
	
	@GetMapping("/packages")
	// @PreAuthorize("hasAnyAuthority('admin')")
	public List<SubscriptionPackage> getSubscriptionList(@RequestParam(required = false) Long operatorId) {
		LOGGER.debug("Get Request to view Subscription List");
		return spServices.getPackageList(operatorId);
	}
	
	@GetMapping("/packages/{packageId}")
	public SubscriptionPackage getSubscriptionDetails(@PathVariable(required = false) Long packageId) {
		LOGGER.debug("Get Request to view Subscription  Details");
		return spServices.getPackageDtls(packageId);
	}
	
	@PostMapping("/packages")
	public SubscriptionPackage addSubscription(@RequestBody SubscriptionPackageRequest spRequest) {
		LOGGER.debug("Add Subscription  Details");
		return spServices.addPackage(spRequest);		
	}
	
	@PutMapping("/packages/{packageId}")
	public SubscriptionPackage updateSubscription(@RequestBody SubscriptionPackageRequest spRequest,@PathVariable(required = false) Long packageId) {
		LOGGER.debug("Add Subscription  Details");
		return spServices.updatePackage(spRequest, packageId);
	}
	
	@DeleteMapping("/subscriptions/{packageId}")
	public ServiceResponse deleteSubscription(@PathVariable(required = false) Long packageId) {
		LOGGER.debug("Add Subscription  Details");
		return spServices.deletePackage(packageId);
	}

}
