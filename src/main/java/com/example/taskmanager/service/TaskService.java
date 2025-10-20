package com.example.taskmanager.service;

import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskExecution;
import com.example.taskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service layer for Task business logic.
 * Handles task CRUD operations and command execution.
 */
@Service
public class TaskService {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private CommandValidatorService commandValidator;
    
    @Autowired
    private CommandExecutorService commandExecutor;
    
    /**
     * Get all tasks from the database.
     */
    public List<Task> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskRepository.findAll();
    }
    
    /**
     * Get a task by ID.
     */
    public Task getTaskById(String id) {
        logger.info("Fetching task with id: {}", id);
        return taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }
    
    /**
     * Create a new task after validating the command.
     */
    public Task createTask(Task task) {
        logger.info("Creating new task: {}", task.getName());
        
        // Validate command for security
        commandValidator.validateCommand(task.getCommand());
        
        // Initialize taskExecutions list if null
        if (task.getTaskExecutions() == null) {
            task.setTaskExecutions(new java.util.ArrayList<>());
        }
        
        return taskRepository.save(task);
    }
    
    /**
     * Delete a task by ID.
     */
    public void deleteTask(String id) {
        logger.info("Deleting task with id: {}", id);
        
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        
        taskRepository.deleteById(id);
    }
    
    /**
     * Find tasks by name (case-insensitive partial match).
     */
    public List<Task> findTasksByName(String name) {
        logger.info("Finding tasks with name containing: {}", name);
        List<Task> tasks = taskRepository.findByNameContaining(name);
        
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found with name containing: " + name);
        }
        
        return tasks;
    }
    
    /**
     * Execute a task and store the execution result.
     */
    public Task executeTask(String id) {
        logger.info("Executing task with id: {}", id);
        
        // Get the task
        Task task = getTaskById(id);
        
        // Record start time
        Date startTime = new Date();
        
        // Execute the command
        String output = commandExecutor.executeCommand(task.getCommand());
        
        // Record end time
        Date endTime = new Date();
        
        // Create execution record
        TaskExecution execution = new TaskExecution(startTime, endTime, output);
        
        // Add to task's execution history
        task.getTaskExecutions().add(execution);
        
        // Save updated task
        return taskRepository.save(task);
    }
}
