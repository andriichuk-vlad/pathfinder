package com.xelerit.pathfinder.service.Ride;

import com.xelerit.pathfinder.model.Ride;
import com.xelerit.pathfinder.model.User;
import com.xelerit.pathfinder.repository.RideRepository;
import com.xelerit.pathfinder.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RideServiceImplTest {
    @Mock
    private RideRepository rideRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private RideServiceImpl rideServiceImpl;
    private User driver;
    private User passenger;
    private Ride ride;

    @BeforeEach
    void setUp() {
        driver = new User();
        driver.setId(1L);
        driver.setName("Driver");
        driver.setEmail("driver@example.com");
        driver.setRole(User.Role.DRIVER);
        passenger = new User();
        passenger.setId(2L);
        passenger.setName("Passenger");
        passenger.setEmail("passenger@example.com");
        passenger.setRole(User.Role.PASSENGER);
        ride = new Ride();
        ride.setId(1L);
        ride.setDriver(driver);
        ride.setAvailableSeats(4);
        ride.setTotalSeats(4);
        ride.setPassengers(new ArrayList<>());
    }

    @Test
    void createRide_validData_shouldSuccessfullyCreateRide() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(driver));
        when(userRepository.save(driver)).thenReturn(driver);
        when(rideRepository.save(ride)).thenReturn(ride);

        Ride actual = rideServiceImpl.createRide(ride, driver.getId());
        assertEquals(4, actual.getAvailableSeats());
        assertEquals(User.Role.DRIVER, actual.getDriver().getRole());
        assertEquals(ride.getId(), actual.getId());
    }

    @Test
    void bookRide_validData_shouldSuccessfullyBookRide() {
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(passenger));
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.ofNullable(ride));
        when(rideRepository.save(ride)).thenReturn(ride);

        Ride actual = rideServiceImpl.bookRide(ride.getId(), passenger.getId());
        assertEquals(3, actual.getAvailableSeats());
    }

    @Test
    void bookRide_alreadyBooked_shouldThrowException() {
        ride.getPassengers().add(passenger);
        ride.setAvailableSeats(3);
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(passenger));
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.ofNullable(ride));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> rideServiceImpl.bookRide(ride.getId(), passenger.getId()));
        assertEquals("User with id " + passenger.getId() + " has already booked this ride", exception.getMessage());
    }

    @Test
    void bookRide_noSeatsAvailable_shouldThrowException() {
        ride.setAvailableSeats(0);
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(passenger));
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.ofNullable(ride));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> rideServiceImpl.bookRide(ride.getId(), passenger.getId()));
        assertEquals("No available seats for this ride", exception.getMessage());
    }

    @Test
    void cancelRide_validData_shouldSuccessfullyCancelRide() {
        ride.getPassengers().add(passenger);
        ride.setAvailableSeats(3);
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(passenger));
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.ofNullable(ride));
        when(rideRepository.save(ride)).thenReturn(ride);

        Ride actual = rideServiceImpl.cancelRide(ride.getId(), passenger.getId());
        assertEquals(4, actual.getAvailableSeats());
    }

    @Test
    void cancelRide_absentPassenger_shouldThrowException() {
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(passenger));
        when(rideRepository.findById(ride.getId())).thenReturn(Optional.ofNullable(ride));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> rideServiceImpl.cancelRide(ride.getId(), passenger.getId()));
        assertEquals("User with id " + passenger.getId() + " is not booked for this ride", exception.getMessage());
    }
}