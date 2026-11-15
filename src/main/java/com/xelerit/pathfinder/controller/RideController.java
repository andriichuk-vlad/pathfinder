package com.xelerit.pathfinder.controller;

import com.xelerit.pathfinder.model.Ride;
import com.xelerit.pathfinder.service.Ride.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @GetMapping("/search")
    public ResponseEntity<List<Ride>> findRides(
            @RequestParam("fromCity") String fromCity,
            @RequestParam("toCity") String toCity) {
        List<Ride> rides = rideService.searchRides(fromCity, toCity);
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }

    @PostMapping("/driver/{driverId}")
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride, @PathVariable Long driverId) {
        Ride createdRide = rideService.createRide(ride, driverId);
        return new ResponseEntity<>(createdRide, HttpStatus.CREATED);
    }

    @PostMapping("/{rideId}/book/{userId}")
    public ResponseEntity<Ride> bookRide(@PathVariable Long rideId, @PathVariable Long userId) {
        Ride bookedRide = rideService.bookRide(rideId, userId);
        return new ResponseEntity<>(bookedRide, HttpStatus.OK);
    }

    @PostMapping("/{rideId}/cancel/{userId}")
    public ResponseEntity<Ride> cancelRide(@PathVariable Long rideId, @PathVariable Long userId) {
        Ride canceledRide = rideService.cancelRide(rideId, userId);
        return new ResponseEntity<>(canceledRide, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Ride>> findAllRides() {
        List<Ride> rides = rideService.findAllRides();
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }
}
