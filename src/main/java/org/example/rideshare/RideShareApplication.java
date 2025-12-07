package org.example.rideshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the SwiftShift-Engine application.
 * Spring Boot will auto-configure based on the dependencies.
 */
@SpringBootApplication
public class RideShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(RideShareApplication.class, args);
        System.out.println("SwiftShift-Engine is running on port 8081");
    }
}
