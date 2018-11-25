package com.deeps.easycable.api.response;

public class ComponentStatus {

	private String apiStatus;
	
	private String dbstatus;

	public String getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}

	public String getDbstatus() {
		return dbstatus;
	}

	public void setDbstatus(String dbstatus) {
		this.dbstatus = dbstatus;
	}

	public ComponentStatus(String apiStatus, String dbstatus) {
		super();
		this.apiStatus = apiStatus;
		this.dbstatus = dbstatus;
	}

	@Override
	public String toString() {
		return "ComponentStatus [apiStatus=" + apiStatus + ", dbstatus=" + dbstatus + "]";
	}

}
