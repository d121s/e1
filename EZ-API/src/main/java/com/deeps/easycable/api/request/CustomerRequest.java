package com.deeps.easycable.api.request;

public class CustomerRequest {

	private String customerName;

	private Long operatorId;

	private Long packageId;

	private String boxId;

	private String cardNumber;

	private String manufacturer;

	private String phoneNumber;

	private String AadharNumber;

	private String Zone;

	private String Address;

	private String Code;

	private String status;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAadharNumber() {
		return AadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		AadharNumber = aadharNumber;
	}

	public String getZone() {
		return Zone;
	}

	public void setZone(String zone) {
		Zone = zone;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CustomerRequest [customerName=" + customerName + ", operatorId=" + operatorId + ", packageId="
				+ packageId + ", boxId=" + boxId + ", cardNumber=" + cardNumber + ", manufacturer=" + manufacturer
				+ ", phoneNumber=" + phoneNumber + ", AadharNumber=" + AadharNumber + ", Zone=" + Zone + ", Address="
				+ Address + ", Code=" + Code + ", status=" + status + "]";
	}

	public CustomerRequest() {
	}

}
