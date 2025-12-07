package org.example.rideshare.service;

import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.ForbiddenException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.model.RideStatus;
import org.example.rideshare.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for all ride-related operations.
 * Handles ride creation, acceptance, completion, and retrieval.
 */
@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    /**
     * Create a new ride request.
     * Only passengers (ROLE_USER) should call this.
     */
    public RideResponse createRide(CreateRideRequest request, String userId) {
        Ride ride = new Ride();
        ride.setUserId(userId);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus(RideStatus.REQUESTED);
        ride.setCreatedAt(new Date());
        // driverId stays null until a driver accepts

        Ride savedRide = rideRepository.save(ride);
        return mapToResponse(savedRide);
    }

    /**
     * Get all rides that are waiting for a driver.
     * Used by drivers to see available ride requests.
     */
    public List<RideResponse> getPendingRides() {
        List<Ride> rides = rideRepository.findByStatus(RideStatus.REQUESTED);
        return rides.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Driver accepts a ride request.
     * Sets the driver id and changes status to ACCEPTED.
     */
    public RideResponse acceptRide(String rideId, String driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        // Make sure ride is still available
        if (!RideStatus.REQUESTED.equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not available for acceptance");
        }

        ride.setDriverId(driverId);
        ride.setStatus(RideStatus.ACCEPTED);

        Ride updatedRide = rideRepository.save(ride);
        return mapToResponse(updatedRide);
    }

    /**
     * Mark a ride as completed.
     * Either the driver or the passenger can complete the ride.
     */
    public RideResponse completeRide(String rideId, String userId, String userRole) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        // Ride must be in ACCEPTED state to be completed
        if (!RideStatus.ACCEPTED.equals(ride.getStatus())) {
            throw new BadRequestException("Ride must be accepted before it can be completed");
        }

        // Check if user is authorized to complete this ride
        boolean isPassenger = ride.getUserId().equals(userId);
        boolean isDriver = ride.getDriverId() != null && ride.getDriverId().equals(userId);

        if (!isPassenger && !isDriver) {
            throw new ForbiddenException("You are not authorized to complete this ride");
        }

        ride.setStatus(RideStatus.COMPLETED);

        Ride updatedRide = rideRepository.save(ride);
        return mapToResponse(updatedRide);
    }

    /**
     * Get all rides for a specific user (passenger).
     * Returns rides in all statuses.
     */
    public List<RideResponse> getUserRides(String userId) {
        List<Ride> rides = rideRepository.findByUserId(userId);
        return rides.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a single ride by ID.
     */
    public RideResponse getRideById(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));
        return mapToResponse(ride);
    }

    // Helper to convert entity to response DTO
    private RideResponse mapToResponse(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getUserId(),
                ride.getDriverId(),
                ride.getPickupLocation(),
                ride.getDropLocation(),
                ride.getStatus(),
                ride.getCreatedAt()
        );
    }
}
