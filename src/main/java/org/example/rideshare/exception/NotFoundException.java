package org.example.rideshare.exception;

/**
 * Thrown when a requested resource doesn't exist.
 * Results in HTTP 404 response.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
