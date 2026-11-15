package com.xelerit.pathfinder.repository;

import com.xelerit.pathfinder.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride,Long> {

    @Query("SELECT r FROM Ride r WHERE (r.fromCity = :fromCity AND r.toCity = :toCity)"
            + "OR (r.fromCity = :fromCity AND :toCity MEMBER OF r.intermediateCities)"
            + "OR (:fromCity MEMBER OF r.intermediateCities AND r.toCity = :toCity)"
    )
    List<Ride> findRides(String fromCity, String toCity);

}
