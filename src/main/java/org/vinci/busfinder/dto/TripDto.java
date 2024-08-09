package org.vinci.busfinder.dto;


public class TripDto {

    private int tripId;
    private int routeId;
    private String serviceId;
    private String tripHeadsign;
    private String tripShortName;
    private String directionId;
    private String blockId;
    private int shapeId;
    private String wheelchairAccessible;

    public TripDto() {}

    public TripDto(int tripId, int routeId, String serviceId, String tripHeadsign, String tripShortName,
                   String directionId, String blockId, int shapeId, String wheelchairAccessible) {
        this.tripId = tripId;
        this.routeId = routeId;
        this.serviceId = serviceId;
        this.tripHeadsign = tripHeadsign;
        this.tripShortName = tripShortName;
        this.directionId = directionId;
        this.blockId = blockId;
        this.shapeId = shapeId;
        this.wheelchairAccessible = wheelchairAccessible;
    }

    @Override
    public String toString() {
        return String.format(
                "Trip[trip_id=%d, route_id=%d, trip_headsign=%s, shape_id=%s, service_id=%s]",
                tripId, routeId, tripHeadsign, shapeId, serviceId
        );
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
    }

    public String getTripShortName() {
        return tripShortName;
    }

    public void setTripShortName(String tripShortName) {
        this.tripShortName = tripShortName;
    }

    public String getDirectionId() {
        return directionId;
    }

    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public int getShapeId() {
        return shapeId;
    }

    public void setShapeId(int shapeId) {
        this.shapeId = shapeId;
    }

    public String getWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public void setWheelchairAccessible(String wheelchairAccessible) {
        this.wheelchairAccessible = wheelchairAccessible;
    }
}
