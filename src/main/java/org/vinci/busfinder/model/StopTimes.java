package org.vinci.busfinder.model;

import jakarta.persistence.*;
import org.vinci.busfinder.model.key.StopTimesKey;

import java.time.LocalTime;

@Entity
@Table(name = "stop_times")
public class StopTimes {

    @EmbeddedId
    private StopTimesKey id;

    @Column(name = "arrival_time", columnDefinition = "TIME")
    private String arrivalTime;

    @Column(name = "departure_time", columnDefinition = "TIME")
    private String departureTime;

    @Column(name = "stop_Headsign")
    private String stopHeadsign;

    @Column(name = "pickup_type")
    private int pickupType;

    @Column(name = "drop_off_type")
    private int dropOffType;

    @Column(name = "shape_dist_traveled")
    private String shapeDistTraveled;

    public StopTimes() {}

    @Override
    public String toString() {
        return String.format(
                "StopTimes[trip_id=%d, stop_id=%d, arrival_time=%s, departure_time=%s, stop_seq=%d, stop_headsign=%s]",
                id.getTripId(), id.getStopId(), arrivalTime.toString(), departureTime.toString(), id.getStopSeq(), stopHeadsign
        );
    }

    public StopTimesKey getId() {
        return id;
    }

    public void setId(StopTimesKey id) {
        this.id = id;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getStopHeadsign() {
        return stopHeadsign;
    }

    public void setStopHeadsign(String stopHeadsign) {
        this.stopHeadsign = stopHeadsign;
    }

    public int getPickupType() {
        return pickupType;
    }

    public void setPickupType(int pickupType) {
        this.pickupType = pickupType;
    }

    public int getDropOffType() {
        return dropOffType;
    }

    public void setDropOffType(int dropOffType) {
        this.dropOffType = dropOffType;
    }

    public String getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(String shapeDistTraveled) {
        this.shapeDistTraveled = shapeDistTraveled;
    }

}
