package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class.
 * Entry point for the Task Manager REST API.
 */
@SpringBootApplication
public class TaskmanagerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TaskmanagerApplication.class, args);
        System.out.println("\n" +
            "========================================\n" +
            "  Task Manager API Started Successfully\n" +
            "  Server: http://localhost:8080\n" +
            "  API Base: http://localhost:8080/tasks\n" +
            "========================================\n");
    }
}
