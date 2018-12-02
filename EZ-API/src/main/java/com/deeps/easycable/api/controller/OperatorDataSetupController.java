package com.deeps.easycable.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deeps.easycable.api.response.ServiceResponse;
import com.deeps.easycable.api.service.OperatorDataSetupService;

@RestController
public class OperatorDataSetupController {
	
	@Autowired
	OperatorDataSetupService odsService;
	
	@PostMapping("/loadData")
	public ServiceResponse uploadData(@RequestParam("file") MultipartFile file,@RequestParam Long operatorId) {
		return odsService.setupOperatorData(file,operatorId);
	}
	

}
