package com.demo.app.employeeDepartmentCRUDOps.dto;

import java.io.Serializable;

public class ResponseVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer statusCode;
	private String statusMessage;
	private String message;
	private Object data;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ResponseVO(Integer statusCode, String statusMessage, String message, Object data) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.message = message;
		this.data = data;
	}
	
	
}
