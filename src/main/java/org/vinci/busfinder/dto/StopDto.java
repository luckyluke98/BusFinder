package org.vinci.busfinder.dto;

public class StopDto {

    private int stopId;
    private int stopCode;
    private String stopName;
    private String stopDescription;
    private String stopLat;
    private String stopLong;
    private String zoneId;
    private String stopUrl;
    private String locationType;
    private String parentStation;
    private String stopTimezone;
    private String wheelchairBoarding;

    public StopDto() {}

    public StopDto(int stopId, int stopCode, String stopName, String stopDescription, String stopLat, String stopLong,
                   String zoneId, String stopUrl, String locationType, String parentStation, String stopTimezone,
                   String wheelchairBoarding) {
        this.stopId = stopId;
        this.stopCode = stopCode;
        this.stopName = stopName;
        this.stopDescription = stopDescription;
        this.stopLat = stopLat;
        this.stopLong = stopLong;
        this.zoneId = zoneId;
        this.stopUrl = stopUrl;
        this.locationType = locationType;
        this.parentStation = parentStation;
        this.stopTimezone = stopTimezone;
        this.wheelchairBoarding = wheelchairBoarding;
    }

    @Override
    public String toString() {
        return String.format(
                "Stop[stop_id=%d, stop_name=%s]",
                stopId, stopName
        );
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public int getStopCode() {
        return stopCode;
    }

    public void setStopCode(int stopCode) {
        this.stopCode = stopCode;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopDescription() {
        return stopDescription;
    }

    public void setStopDescription(String stopDescription) {
        this.stopDescription = stopDescription;
    }

    public String getStopLat() {
        return stopLat;
    }

    public void setStopLat(String stopLat) {
        this.stopLat = stopLat;
    }

    public String getStopLong() {
        return stopLong;
    }

    public void setStopLong(String stopLong) {
        this.stopLong = stopLong;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getStopUrl() {
        return stopUrl;
    }

    public void setStopUrl(String stopUrl) {
        this.stopUrl = stopUrl;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getParentStation() {
        return parentStation;
    }

    public void setParentStation(String parentStation) {
        this.parentStation = parentStation;
    }

    public String getStopTimezone() {
        return stopTimezone;
    }

    public void setStopTimezone(String stopTimezone) {
        this.stopTimezone = stopTimezone;
    }

    public String getWheelchairBoarding() {
        return wheelchairBoarding;
    }

    public void setWheelchairBoarding(String wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
    }
}
