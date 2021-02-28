package com.shopbridge.item.response;

public class ItemResponse {
	
	private Object data;
	private Boolean error;
	private String message;
	
	public ItemResponse() {
		super();
	}
	public ItemResponse(Object data, Boolean error, String message) {
		super();
		this.data = data;
		this.error = error;
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
