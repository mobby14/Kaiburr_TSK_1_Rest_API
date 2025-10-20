package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TaskExecution represents a single execution instance of a task.
 * It stores the start time, end time, and output of the command execution.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExecution {
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date startTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date endTime;
    
    private String output;
}
