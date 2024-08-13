package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.model.key.StopTimesKey;

import java.util.List;

@Repository
public interface StopTimesRepository extends JpaRepository<StopTimes, StopTimesKey> {

    @Query("SELECT id.tripId FROM StopTimes WHERE id.stopId = ?1")
    public List<Integer> findTripIdByStopId(int stopId);

}
