package org.example.rideshare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO for returning ride information to clients.
 * Used for both single ride responses and lists.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideResponse {

    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private String status;
    private Date createdAt;
}
