package com.timechaser.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(CreateException.class)
	public ResponseEntity<?> handleCreationException(CreateException e, WebRequest request){
		logger.error("Creation Exception occurred: {}, Request Details: {}", e.getMessage(), request.getDescription(false), e);
		
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(), 
				request.getDescription(false),
				((ServletWebRequest) request).getHttpMethod().name());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({
		UserNotFoundException.class, 
		RoleNotFoundException.class
	})
	public ResponseEntity<?> handleNotFoundException(Exception e, WebRequest request){
		logger.error("Not Found Exception occurred: {}, Request Details: {}", e.getMessage(), request.getDescription(false), e);
		
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(), 
				request.getDescription(false),
				((ServletWebRequest) request).getHttpMethod().name());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({
		AccessDeniedException.class, 
		BadCredentialsException.class
	})
	public ResponseEntity<?> handleAuthenticationException(Exception e, WebRequest request){
		logger.error("Authentication Exception Thrown: {} , Request Details: {}", e.getMessage(), request.getDescription(false));
		
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.UNAUTHORIZED.value(), 
				e.getMessage(), 
				request.getDescription(false),
				((ServletWebRequest) request).getHttpMethod().name());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({
		UserUpdateDetailsException.class, 
		UserUpdatePasswordException.class, 
		Exception.class
	})	
	public ResponseEntity<?> handleException(Exception e, WebRequest request){
		logger.error("Exception occurred: {}, Request Details: {}", e.getMessage(), request.getDescription(false), e);
		
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				e.getMessage(), 
				request.getDescription(false),
				((ServletWebRequest) request).getHttpMethod().name());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> validationError(MethodArgumentNotValidException e, WebRequest request) {
		logger.error("Validation Exception occurred: {}, Request Details: {}", e.getMessage(), request.getDescription(false), e);

	    StringBuilder builder = new StringBuilder();
	    
		BindingResult result = e.getBindingResult();
		
	    List<FieldError> errors = result.getFieldErrors();
	    for (FieldError error : errors ) {
	       builder.append(error.getField() + " : " + error.getDefaultMessage());
	    } 
	    
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(), 
				builder.toString(), 
				request.getDescription(false),
				((ServletWebRequest) request).getHttpMethod().name());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
