package com.deeps.easycable.api.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class HealthStatus {

	private String status;
	
	private ComponentStatus componentStatus;
	
	
}
