package com.timechaser.exception;

public class AccessDeniedException extends RuntimeException{
	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}
}
