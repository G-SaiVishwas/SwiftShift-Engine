package org.example.rideshare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new ride request.
 * The passenger provides pickup and drop locations, everything else is set by the server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRideRequest {

    @NotBlank(message = "Pickup location is required")
    @Size(min = 2, message = "Pickup location must be at least 2 characters")
    private String pickupLocation;

    @NotBlank(message = "Drop location is required")
    @Size(min = 2, message = "Drop location must be at least 2 characters")
    private String dropLocation;
}
