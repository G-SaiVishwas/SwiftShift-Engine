package org.example.rideshare.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Standard error response format for all API errors.
 * Follows a consistent structure that clients can depend on.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String error;
    private String message;
    private String timestamp;

    // Factory method to create error responses with current timestamp
    public static ErrorResponse of(String error, String message) {
        return new ErrorResponse(error, message, Instant.now().toString());
    }
}
