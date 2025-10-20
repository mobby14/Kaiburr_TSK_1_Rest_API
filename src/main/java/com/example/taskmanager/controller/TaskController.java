package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Task Management API.
 * Provides endpoints for task CRUD operations and command execution.
 */
@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    
    @Autowired
    private TaskService taskService;
    
    /**
     * GET /tasks - Get all tasks
     * GET /tasks?id={id} - Get task by ID
     */
    @GetMapping
    public ResponseEntity<Object> getTasks(@RequestParam(required = false) String id) {
        logger.info("GET /tasks - id: {}", id);
        
        if (id != null && !id.isEmpty()) {
            // Get single task by ID
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } else {
            // Get all tasks
            List<Task> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        }
    }
    
    /**
     * PUT /tasks - Create a new task
     */
    @PutMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        logger.info("PUT /tasks - Creating task: {}", task.getName());
        
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    
    
    /**
     * DELETE /tasks/{id} - Delete a task
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        logger.info("DELETE /tasks/{}", id);
        
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * GET /tasks/search?name={name} - Find tasks by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Task>> findTasksByName(@RequestParam String name) {
        logger.info("GET /tasks/search - name: {}", name);
        
        List<Task> tasks = taskService.findTasksByName(name);
        return ResponseEntity.ok(tasks);
    }
    
    /**
     * POST /tasks - Create a new task where the server generates the ID
     */
    @PostMapping
    public ResponseEntity<Task> createTaskWithServerId(@Valid @RequestBody Task task) {
    logger.info("POST /tasks - Creating task with server-generated ID: {}", task.getName());
    
    // Ensure the client does not provide an ID.
    // The database will generate it automatically.
    task.setId(null); 
    
    Task createdTask = taskService.createTask(task);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
}
    /**
     * PUT /tasks/{id}/execute - Execute a task
     */
    @PutMapping("/{id}/execute")
    public ResponseEntity<Task> executeTask(@PathVariable String id) {
        logger.info("PUT /tasks/{}/execute", id);
        
        Task updatedTask = taskService.executeTask(id);
        return ResponseEntity.ok(updatedTask);
    }
}
