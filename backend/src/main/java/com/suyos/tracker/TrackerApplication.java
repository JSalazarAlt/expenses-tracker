package com.suyos.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the Expense Tracker system.
 * 
 * This class serves as the entry point for the Spring Boot application,
 * enabling auto-configuration and component scanning for the entire
 * com.suyos.tracker package and its sub-packages.
 * 
 * @author Joel Salazar
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class TrackerApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * 
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(TrackerApplication.class, args);
	}

}
