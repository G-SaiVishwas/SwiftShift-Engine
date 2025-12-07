package org.example.rideshare.exception;

/**
 * Thrown when authentication fails or user lacks proper credentials.
 * Results in HTTP 401 response.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
