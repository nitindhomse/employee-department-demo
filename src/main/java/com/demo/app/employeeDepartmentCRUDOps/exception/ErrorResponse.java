package com.demo.app.employeeDepartmentCRUDOps.exception;

public class ErrorResponse {
	
	private Integer statusCode;
	private String statusMessage = "failed";
	private String errorMessage;
	private String timestamp;
	
	public Integer getStatusCode() {
		return statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	

}
