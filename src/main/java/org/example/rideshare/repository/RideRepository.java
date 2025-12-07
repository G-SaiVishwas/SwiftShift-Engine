package org.example.rideshare.repository;

import org.example.rideshare.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Ride entity operations.
 * Custom queries for filtering rides by user, driver, and status.
 */
@Repository
public interface RideRepository extends MongoRepository<Ride, String> {

    // Get all rides for a specific passenger
    List<Ride> findByUserId(String userId);

    // Get all rides for a specific driver
    List<Ride> findByDriverId(String driverId);

    // Get rides by status (REQUESTED, ACCEPTED, COMPLETED)
    List<Ride> findByStatus(String status);

    // Get rides by user and status - useful for checking active rides
    List<Ride> findByUserIdAndStatus(String userId, String status);
}
