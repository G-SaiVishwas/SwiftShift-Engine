package org.example.rideshare.exception;

/**
 * Thrown when user is authenticated but doesn't have permission for the action.
 * Results in HTTP 403 response.
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
