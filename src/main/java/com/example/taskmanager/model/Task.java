package com.example.taskmanager.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Task represents a shell command that can be executed in a Kubernetes pod.
 * It contains task metadata and a history of all executions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {
    
    @Id
    private String id;
    
    @NotBlank(message = "Task name is required")
    private String name;
    
    @NotBlank(message = "Owner name is required")
    private String owner;
    
    @NotBlank(message = "Command is required")
    private String command;
    
    private List<TaskExecution> taskExecutions = new ArrayList<>();
}
