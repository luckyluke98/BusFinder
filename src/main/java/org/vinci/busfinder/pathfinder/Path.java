package org.vinci.busfinder.pathfinder;

import org.vinci.busfinder.model.StopTimes;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<StopTimes> stops;

    public Path() {
        stops = new ArrayList<StopTimes>();
    }

    public Boolean addStopTimes(StopTimes st) {
        return stops.add(st);
    }

    public List<StopTimes> getStops() {
        return stops;
    }

    public void setStops(List<StopTimes> stops) {
        this.stops = stops;
    }
}
