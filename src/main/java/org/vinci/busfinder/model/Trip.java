package org.vinci.busfinder.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @Column(name = "trip_id")
    private int tripId;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Calendar calendar;

    @Column(name = "trip_headsign")
    private String tripHeadsign;

    @Column(name = "trip_short_name")
    private String tripShortName;

    @Column(name = "direction_id")
    private String directionId;

    @Column(name = "block_id")
    private String blockId;

    @OneToOne
    @JoinColumn(name = "shape_id")
    private Shape shapeId;

    @Column(name = "wheelchair_accessible")
    private String wheelchairAccessible;

    @OneToMany(mappedBy = "trip")
    private Set<StopTimes> stopTimes;

    public Trip() {}

    @Override
    public String toString() {
        return String.format(
                "Trip[trip_id=%d, trip_headsign=%s]",
                tripId, tripHeadsign
        );
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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

    public String getWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public void setWheelchairAccessible(String wheelchairAccessible) {
        this.wheelchairAccessible = wheelchairAccessible;
    }

    public Shape getShapeId() {
        return shapeId;
    }

    public void setShapeId(Shape shapeId) {
        this.shapeId = shapeId;
    }
}
