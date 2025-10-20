package com.example.taskmanager.service;

import com.example.taskmanager.exception.CommandExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Service for executing shell commands securely using ProcessBuilder.
 * Uses ProcessBuilder instead of Runtime.exec() for better security.
 */
@Service
public class CommandExecutorService {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutorService.class);
    private static final int TIMEOUT_SECONDS = 30;
    
    /**
     * Executes a shell command and returns the output.
     * Uses ProcessBuilder with explicit argument separation for security.
     * 
     * @param command The command to execute
     * @return The command output as a string
     * @throws CommandExecutionException if execution fails
     */
    public String executeCommand(String command) {
        logger.info("Executing command: {}", command);
        
        try {
            // Parse command and arguments
            String[] commandParts = command.trim().split("\\s+");
            
            // Use ProcessBuilder for secure command execution
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);
            processBuilder.redirectErrorStream(true); // Merge error and output streams
            
            // Start the process
            Process process = processBuilder.start();
            
            // Read output
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            
            // Wait for completion with timeout
            boolean completed = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            
            if (!completed) {
                process.destroyForcibly();
                throw new CommandExecutionException(
                    "Command execution timed out after " + TIMEOUT_SECONDS + " seconds",
                    null
                );
            }
            
            // Check exit code
            int exitCode = process.exitValue();
            if (exitCode != 0) {
                logger.warn("Command exited with code: {}", exitCode);
                output.append("\nExit code: ").append(exitCode);
            }
            
            String result = output.toString().trim();
            logger.info("Command execution completed. Output length: {} characters", result.length());
            
            return result.isEmpty() ? "Command executed successfully (no output)" : result;
            
        } catch (IOException e) {
            logger.error("IO error during command execution", e);
            throw new CommandExecutionException("Failed to execute command: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Command execution interrupted", e);
            throw new CommandExecutionException("Command execution was interrupted", e);
        }
    }
}
