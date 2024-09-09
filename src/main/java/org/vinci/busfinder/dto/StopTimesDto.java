package org.vinci.busfinder.dto;

public class StopTimesDto {

    private int tripId;
    private int stopId;
    private int stopSeq;
    private String arrivalTime;
    private String departureTime;
    private String stopHeadsign;
    private int pickupType;
    private int dropOffType;
    private String shapeDistTraveled;

    public StopTimesDto() {}

    public StopTimesDto(int tripId, int stopId, int stopSeq, String arrivalTime, String departureTime, String stopHeadsign, int pickupType, int dropOffType, String shapeDistTraveled) {
        this.tripId = tripId;
        this.stopId = stopId;
        this.stopSeq = stopSeq;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopHeadsign = stopHeadsign;
        this.pickupType = pickupType;
        this.dropOffType = dropOffType;
        this.shapeDistTraveled = shapeDistTraveled;
    }

    @Override
    public String toString() {
        return String.format(
                "StopTimes[trip_id=%d, stop_id=%d, arrival_time=%s, departure_time=%s, stop_seq=%d, stop_headsign=%s]",
                tripId, stopId, arrivalTime, departureTime, stopSeq, stopHeadsign
        );
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public int getStopSeq() {
        return stopSeq;
    }

    public void setStopSeq(int stopSeq) {
        this.stopSeq = stopSeq;
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
