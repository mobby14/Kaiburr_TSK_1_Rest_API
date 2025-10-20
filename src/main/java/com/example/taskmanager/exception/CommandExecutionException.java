package com.example.taskmanager.exception;

/**
 * Exception thrown when command execution fails.
 */
public class CommandExecutionException extends RuntimeException {
    
    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
