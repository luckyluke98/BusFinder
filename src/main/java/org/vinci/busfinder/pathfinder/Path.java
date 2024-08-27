package org.vinci.busfinder.pathfinder;

import org.vinci.busfinder.model.StopTimes;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<Integer> stops;

    public Path() {
        stops = new ArrayList<Integer>();
    }

    public Path(Path path) {
        stops = new ArrayList<>(path.getStops());
    }

    public Boolean add(Integer st) {
        return stops.add(st);
    }

    public List<Integer> getStops() {
        return stops;
    }

    public void setStops(List<Integer> stops) {
        this.stops = stops;
    }
}
