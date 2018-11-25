package com.deeps.easycable.api.response;

public class HealthStatus {

	private String status;
	
	private ComponentStatus componentStatus;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public ComponentStatus getComponentsStatus() {
		return componentStatus;
	}

	public void setComponentsStatus(ComponentStatus componentStatus) {
		this.componentStatus = componentStatus;
	}

	public HealthStatus(String status, ComponentStatus componentStatus) {
		super();
		this.status = status;
		this.componentStatus = componentStatus;
	}		
}
