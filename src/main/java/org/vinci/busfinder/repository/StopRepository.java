package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.dto.ShapeDto;
import org.vinci.busfinder.dto.StopDto;
import org.vinci.busfinder.model.Stop;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Integer> {

    @Modifying
    @Query(value = "INSERT INTO stops (stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url," +
            "location_type,parent_station,stop_timezone,wheelchair_boarding)" +
            "VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)", nativeQuery = true)
    void saveRaw(int stopId, int stopCode, String stopName, String stopDescription, String stopLat, String stopLong,
                 String zoneId, String stopUrl, String locationType, String parentStation, String stopTimezone,
                 String wheelchairBoarding);

    default void saveAllRaw(List<StopDto> stops) {
        stops.forEach(stop -> {
            saveRaw(stop.getStopId(), stop.getStopCode(), stop.getStopName(), stop.getStopDescription(),
                    stop.getStopLat(), stop.getStopLong(), stop.getZoneId(), stop.getStopUrl(), stop.getLocationType(),
                    stop.getParentStation(), stop.getStopTimezone(), stop.getWheelchairBoarding());
        });
    }

}
