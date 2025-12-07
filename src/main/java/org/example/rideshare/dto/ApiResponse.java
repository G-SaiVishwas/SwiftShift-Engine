package org.example.rideshare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response wrapper.
 * Useful for returning simple success/failure messages.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private boolean success;
    private String message;

    // Quick factory method for success responses
    public static ApiResponse success(String message) {
        return new ApiResponse(true, message);
    }

    // Quick factory method for error responses
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message);
    }
}
