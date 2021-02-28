package com.shopbridge.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shopbridge.item.response.ItemResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
		
	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<ItemResponse> duplicateRetailerAndTemplateType(ResourceAlreadyExistException ex) {
		ItemResponse response = new ItemResponse();
		response.setMessage(ex.getMessage());
		response.setError(true);
		return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ItemResponse> resourceNotFound(ResourceNotFoundException ex) {
		ItemResponse response = new ItemResponse();
		response.setMessage(ex.getMessage());
		response.setError(true);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
