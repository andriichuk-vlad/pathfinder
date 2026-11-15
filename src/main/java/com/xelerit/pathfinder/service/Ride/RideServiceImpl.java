package com.xelerit.pathfinder.service.Ride;

import com.xelerit.pathfinder.model.Ride;
import com.xelerit.pathfinder.model.User;
import com.xelerit.pathfinder.repository.RideRepository;
import com.xelerit.pathfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    @Override
    public List<Ride> searchRides(String fromCity, String toCity) {
        return rideRepository.findRides(fromCity, toCity);
    }

    @Override
    public Ride createRide(Ride ride, Long driverId) {
        User driver = userRepository.findById(driverId).orElseThrow(
                () -> new RuntimeException("Driver with id " + driverId + " not found"));
        driver.setRole(User.Role.DRIVER);
        userRepository.save(driver);
        ride.setDriver(driver);
        ride.setAvailableSeats(ride.getTotalSeats());
        return rideRepository.save(ride);
    }

    @Override
    public Ride bookRide(Long rideId, Long userId) {
        Ride ride = getRide(rideId);
        User user = getUser(userId);
        if (ride.getDriver().getId().equals(userId)) {
            throw new RuntimeException("Driver cannot book their own ride");
        }
        if (ride.getAvailableSeats() <= 0) {
            throw new RuntimeException("No available seats for this ride");
        }
        if (ride.getPassengers().contains(user)) {
            throw new RuntimeException("User with id " + userId + " has already booked this ride");
        }
        ride.getPassengers().add(user);
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        return rideRepository.save(ride);
    }

    @Override
    public Ride cancelRide(Long rideId, Long userId) {
        Ride ride = getRide(rideId);
        User user = getUser(userId);
        if (!ride.getPassengers().contains(user)) {
            throw new RuntimeException("User with id " + userId + " is not booked for this ride");
        }
        ride.getPassengers().remove(user);
        ride.setAvailableSeats(ride.getAvailableSeats() + 1);
        rideRepository.save(ride);
        return ride;
    }

    @Override
    public List<Ride> findAllRides() {
        return rideRepository.findAll();
    }

    private Ride getRide(Long rideId) {
        return rideRepository.findById(rideId).orElseThrow(
                () -> new RuntimeException("Ride with id " + rideId + " not found"));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User with id " + userId + " not found"));
    }
}
