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

    @Query("SELECT st.id.stopId FROM StopTimes st WHERE st.id.tripId = ?1 ORDER BY st.id.stopSeq")
    public List<Integer> findStopIdsByTripId(int tripId);

    @Query("SELECT st.id.tripId FROM StopTimes st WHERE st.id.stopId = ?1 AND st.departureTime BETWEEN ?2 AND ?3")
    public List<Integer> findTripIdByStopIdAndTime(int stopId, String time1, String time2);


}
