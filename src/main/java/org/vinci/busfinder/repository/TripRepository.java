package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
}
