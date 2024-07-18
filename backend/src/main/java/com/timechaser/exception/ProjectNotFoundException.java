package com.timechaser.exception;

public class ProjectNotFoundException extends RuntimeException{
	public ProjectNotFoundException(String message) {
		super(message);
	}
}