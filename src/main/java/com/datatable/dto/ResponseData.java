package com.datatable.dto;

public class ResponseData<T> {

	private String status;
	
	private String errorMessage;
	
	private T responseList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getResponseList() {
		return responseList;
	}

	public void setResponseList(T responseList) {
		this.responseList = responseList;
	}
	
	
	
}
