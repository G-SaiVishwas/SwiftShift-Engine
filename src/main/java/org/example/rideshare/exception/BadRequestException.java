package org.example.rideshare.exception;

/**
 * Thrown when the client sends invalid data or makes an invalid request.
 * Results in HTTP 400 response.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
