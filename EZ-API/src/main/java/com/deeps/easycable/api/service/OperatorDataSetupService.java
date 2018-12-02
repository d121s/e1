package com.deeps.easycable.api.service;

import org.springframework.web.multipart.MultipartFile;

import com.deeps.easycable.api.response.ServiceResponse;

public interface OperatorDataSetupService {
	public ServiceResponse setupOperatorData(MultipartFile file,Long operatorId);
}
