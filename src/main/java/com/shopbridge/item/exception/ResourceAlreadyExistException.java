package com.shopbridge.item.exception;

public class ResourceAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -4250812576507248980L;

	public ResourceAlreadyExistException(String message) {
		super(message);
	}
}
