package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.Trip;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    @Query("SELECT t.tripId FROM Trip t")
    public List<Integer> findAllTripId();

    @Query("SELECT t.tripId FROM Trip t WHERE t.routeId = ?1")
    public List<Integer> findTripIdsByRouteId(int routeId);

}
