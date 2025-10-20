package com.example.taskmanager.exception;

/**
 * Exception thrown when a task is not found in the database.
 */
public class TaskNotFoundException extends RuntimeException {
    
    public TaskNotFoundException(String id) {
        super("Task not found with id: " + id);
    }
}
