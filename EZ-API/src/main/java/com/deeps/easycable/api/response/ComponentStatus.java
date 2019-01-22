package com.deeps.easycable.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.AccessLevel;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ComponentStatus {

	private String apiStatus;
	
	private String dbstatus;

}
