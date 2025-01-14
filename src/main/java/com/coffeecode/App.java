package com.coffeecode;

import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            // Ensure logs directory exists
            Files.createDirectories(Path.of("logs"));
            Files.createDirectories(Path.of("logs/archived"));
            
            logger.info("Binary Search Visualizer starting...");
            logger.debug("Initializing application components...");
            
            // Your application logic here
            
            logger.info("Application started successfully");
        } catch (Exception e) {
            logger.error("Failed to start application", e);
        }
    }
}
