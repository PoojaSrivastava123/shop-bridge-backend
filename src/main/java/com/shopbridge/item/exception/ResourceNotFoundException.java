package com.shopbridge.item.exception;


public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4250812576507248970L;


    public ResourceNotFoundException(Integer id, String message) {
        super(String.format(message, id));
    }
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
