package org.vinci.busfinder.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StopTimesKey implements Serializable {

    @Column(name = "trip_id")
    private int tripId;

    @Column(name = "stop_id")
    private int stopId;

    @Column(name = "stop_sequence")
    private int stopSeq;

    public StopTimesKey() {}

    @Override
    public String toString() {
        return String.format(
                "StopTimesKey[trip_id=%d, stop_id=%d, stop_seq=%s]",
                tripId, stopId, stopSeq
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
}
