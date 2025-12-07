package org.example.rideshare.controller;

import jakarta.validation.Valid;
import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for ride-related endpoints.
 * Different endpoints for passengers and drivers.
 */
@RestController
@RequestMapping("/api/v1")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    /**
     * Create a new ride request.
     * POST /api/v1/rides
     * Only for passengers (ROLE_USER)
     */
    @PostMapping("/rides")
    public ResponseEntity<RideResponse> createRide(@Valid @RequestBody CreateRideRequest request,
                                                   Authentication authentication) {
        // Get user id from authentication credentials
        String userId = (String) authentication.getCredentials();
        RideResponse response = rideService.createRide(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get all rides for the logged-in passenger.
     * GET /api/v1/user/rides
     */
    @GetMapping("/user/rides")
    public ResponseEntity<List<RideResponse>> getUserRides(Authentication authentication) {
        String userId = (String) authentication.getCredentials();
        List<RideResponse> rides = rideService.getUserRides(userId);
        return ResponseEntity.ok(rides);
    }

    /**
     * Get all pending ride requests.
     * GET /api/v1/driver/rides/requests
     * Only for drivers (ROLE_DRIVER)
     */
    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<RideResponse>> getPendingRides() {
        List<RideResponse> rides = rideService.getPendingRides();
        return ResponseEntity.ok(rides);
    }

    /**
     * Accept a ride request.
     * POST /api/v1/driver/rides/{rideId}/accept
     * Only for drivers (ROLE_DRIVER)
     */
    @PostMapping("/driver/rides/{rideId}/accept")
    public ResponseEntity<RideResponse> acceptRide(@PathVariable String rideId,
                                                   Authentication authentication) {
        String driverId = (String) authentication.getCredentials();
        RideResponse response = rideService.acceptRide(rideId, driverId);
        return ResponseEntity.ok(response);
    }

    /**
     * Complete a ride.
     * POST /api/v1/rides/{rideId}/complete
     * Both passengers and drivers can call this.
     */
    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<RideResponse> completeRide(@PathVariable String rideId,
                                                     Authentication authentication) {
        String userId = (String) authentication.getCredentials();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        RideResponse response = rideService.completeRide(rideId, userId, role);
        return ResponseEntity.ok(response);
    }
}
