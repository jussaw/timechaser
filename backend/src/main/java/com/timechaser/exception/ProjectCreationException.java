package com.timechaser.exception;

public class ProjectCreationException extends RuntimeException {
    public ProjectCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}