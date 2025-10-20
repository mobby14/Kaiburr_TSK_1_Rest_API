package com.example.taskmanager.service;

import com.example.taskmanager.exception.InvalidCommandException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service for validating shell commands to prevent command injection attacks.
 * Implements multiple security checks based on OWASP best practices.
 */
@Service
public class CommandValidatorService {
    
    // Dangerous shell metacharacters that could be used for injection
    private static final List<String> DANGEROUS_CHARS = Arrays.asList(
        ";", "&&", "||", "|", "`", "$", "(", ")", 
        ">", "<", "&", "\\n", "\\r", "\\t"
    );
    
    // Whitelist of safe commands (can be extended)
    private static final List<String> ALLOWED_COMMANDS = Arrays.asList(
        "echo", "ls", "pwd", "date", "whoami", "hostname", 
        "cat", "grep", "wc", "head", "tail", "df", "du", "ping"
    );
    
    // Pattern for safe arguments (alphanumeric, spaces, dots, hyphens, underscores, slashes)
    private static final Pattern SAFE_ARG_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s./_-]+$");
    
    /**
     * Validates a command for security concerns.
     * @param command The command to validate
     * @throws InvalidCommandException if the command is unsafe
     */
    public void validateCommand(String command) {
    if (command == null || command.trim().isEmpty()) {
        throw new InvalidCommandException("Command cannot be empty");
    }
    
    // Check for dangerous characters
    for (String dangerousChar : DANGEROUS_CHARS) {
        if (command.contains(dangerousChar)) {
            throw new InvalidCommandException(
                "Command contains dangerous character: " + dangerousChar
            );
        }
    }
    
    // --- FIX 1: Get the first word as a String ---
    String baseCommand = command.trim().split("\\s+")[0];
    
    // --- FIX 2: Check the String against the whitelist ---
    if (!ALLOWED_COMMANDS.contains(baseCommand.toLowerCase())) {
        throw new InvalidCommandException(
            "Command '" + baseCommand + "' is not in the allowed whitelist. " +
            "Allowed commands: " + String.join(", ", ALLOWED_COMMANDS)
        );
    }
    
    // --- FIX 3: Use the String's length to get arguments ---
    String args = command.substring(baseCommand.length()).trim();
    if (!args.isEmpty() && !SAFE_ARG_PATTERN.matcher(args).matches()) {
        throw new InvalidCommandException(
            "Command arguments contain unsafe characters. " +
            "Only alphanumeric, spaces, dots, hyphens, underscores, and slashes are allowed."
        );
    }
}

}
