package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Task CRUD operations.
 * Extends MongoRepository to provide standard database operations.
 */
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    
    /**
     * Find tasks by name containing the search string (case-insensitive).
     * @param name The search string
     * @return List of matching tasks
     */
    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<Task> findByNameContaining(String name);
}
