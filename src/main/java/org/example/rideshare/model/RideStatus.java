package org.example.rideshare.model;

/**
 * Possible states for a ride throughout its lifecycle.
 * Transitions: REQUESTED -> ACCEPTED -> COMPLETED
 */
public class RideStatus {

    // Initial state when a passenger creates a ride request
    public static final String REQUESTED = "REQUESTED";

    // A driver has picked up this ride
    public static final String ACCEPTED = "ACCEPTED";

    // The ride has been finished
    public static final String COMPLETED = "COMPLETED";

    // Private constructor to prevent instantiation
    private RideStatus() {
    }
}
