package org.example.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Ride entity that tracks a ride request from creation to completion.
 * Each ride is linked to a passenger (userId) and optionally a driver (driverId).
 */
@Document(collection = "rides")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    private String id;

    // The passenger who requested this ride
    private String userId;

    // The driver who accepted - null until someone picks it up
    private String driverId;

    // Where the passenger wants to be picked up
    private String pickupLocation;

    // Final destination
    private String dropLocation;

    // Current state: REQUESTED -> ACCEPTED -> COMPLETED
    private String status;

    // When the ride was first requested
    private Date createdAt;
}
