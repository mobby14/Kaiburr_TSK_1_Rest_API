package com.example.taskmanager.exception;

/**
 * Exception thrown when a command contains unsafe or malicious code.
 */
public class InvalidCommandException extends RuntimeException {
    
    public InvalidCommandException(String message) {
        super(message);
    }
}
