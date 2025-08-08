package com.projects.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the Expense Tracker application.
 * This class serves as the entry point for the application and configures
 * the Spring Boot auto-configuration, component scanning, and property support.
 *
 * @author Suyos Team
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class TrackerApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * 
	 * @param args command line arguments passed to the application
	 */
	public static void main(String[] args) {
		// Start the Spring Boot application
		SpringApplication.run(TrackerApplication.class, args);
	}

}
