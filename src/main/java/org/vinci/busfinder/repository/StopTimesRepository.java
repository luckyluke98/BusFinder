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

    @Query("SELECT st " +
            "FROM Trip t " +
            "JOIN StopTimes st ON st.id.tripId = t.tripId " +
            "WHERE t.routeId = ?1 AND st.id.stopId = ?2 AND st.departureTime >= ?3 " +
            "ORDER BY st.departureTime LIMIT 1")
    public StopTimes findDepartureTripsByRouteByStopByTime(int routeId, int stopId, String time);

    @Query("SELECT st FROM StopTimes st WHERE st.id.stopId = ?1 AND st.id.tripId = ?2")
    public StopTimes findStopTimesByStopIdByTripId(int stopId, int tripId);


}
