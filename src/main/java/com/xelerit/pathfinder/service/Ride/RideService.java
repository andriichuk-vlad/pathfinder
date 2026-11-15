package com.xelerit.pathfinder.service.Ride;

import com.xelerit.pathfinder.model.Ride;

import java.util.List;

public interface RideService {

    List<Ride> searchRides(String fromCity, String toCity);

    Ride createRide(Ride ride, Long driverId);

    Ride bookRide(Long rideId, Long userId);

    Ride cancelRide(Long rideId, Long userId);

    List<Ride> findAllRides();
}
