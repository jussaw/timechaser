package com.timechaser.exception;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<?> handleUserCreationException(UserCreationException e, WebRequest request){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserUpdateDetailsException.class)
	public ResponseEntity<?> handleUpdateUserDetailsException(UserUpdateDetailsException e, WebRequest request){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e, WebRequest request){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e, WebRequest request){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> validationError(MethodArgumentNotValidException ex) {
	    StringBuilder builder = new StringBuilder();
	    
		BindingResult result = ex.getBindingResult();
		
	    List<FieldError> errors = result.getFieldErrors();
	    for (FieldError error : errors ) {
	       builder.append(error.getField() + " : " + error.getDefaultMessage());
	    } 
		return new ResponseEntity<>(builder.toString(), HttpStatus.BAD_REQUEST);
	}

}
