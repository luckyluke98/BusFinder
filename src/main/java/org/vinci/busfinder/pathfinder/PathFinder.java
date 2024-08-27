package org.vinci.busfinder.pathfinder;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    private int startStopId;
    private int endStopId;
    private String departureTime;

    private List<Path> paths;

    @Autowired
    private GraphDataManger gdm;

    public PathFinder() {
        this.paths = new ArrayList<>();
    }

    public List<Path> findAllPaths() {
        Path path = new Path();
        boolean[] visitedStop = new boolean[gdm.getStopNetwork().keySet().stream().max(Integer::compare).get()];

        this.paths.clear();

        findAllPathRec(startStopId, path, visitedStop);

        return paths;
    }

    private void findAllPathRec(int curStopId, Path path, boolean[] visitedStop) {

        if (curStopId == endStopId) {
            paths.add(new Path(path));
            return;
        }

        Path path_p = new Path(path);
        visitedStop[curStopId] = true;

        for (Integer adj : gdm.getStopNetwork().get(curStopId)) {
            if (!visitedStop[adj]) {

                path_p.add(adj);
                findAllPathRec(adj, path_p, visitedStop);
            }
        }

        visitedStop[curStopId] = false;
        /////

    }

    private String timeAdd(String time1, String time2) {
        String[] t1Split = time1.split(":");
        String[] t2Split = time2.split(":");

        int t1Seconds = Integer.parseInt(t1Split[2]);
        int t2Seconds = Integer.parseInt(t2Split[2]);

        int tMinCurry = (t1Seconds + t2Seconds)/60;
        int tSeconds = (t1Seconds + t2Seconds)%60;

        int t1Minutes = Integer.parseInt(t1Split[1]);
        int t2Minutes = Integer.parseInt(t2Split[1]);

        int tHCurry = (t1Minutes + t2Minutes + tMinCurry)/60;
        int tMin = (t1Minutes + t2Minutes + tMinCurry)%60;

        int t1Hours = Integer.parseInt(t1Split[0]);
        int t2Hours = Integer.parseInt(t2Split[0]);

        int tHour = t1Hours + t2Hours + tHCurry;

        return tHour + ":" + tMin + ":" + tSeconds;

    }

    private int compareTime(String t1, String t2) {
        if(t1.isEmpty() || t2.isEmpty()) {
            throw new RuntimeException("t1 or t2 is empty");
        }
        String[] t1Split = t1.split(":");
        String[] t2Split = t2.split(":");

        if (Integer.parseInt(t1Split[0]) > Integer.parseInt(t2Split[0])) {
            return -1;
        } else if (Integer.parseInt(t1Split[0]) < Integer.parseInt(t2Split[0])) {
            return 1;
        } else {
            if (Integer.parseInt(t1Split[1]) > Integer.parseInt(t2Split[1])) {
                return -1;
            } else if (Integer.parseInt(t1Split[1]) < Integer.parseInt(t2Split[1])) {
                return 1;
            } else {
                if (Integer.parseInt(t1Split[2]) > Integer.parseInt(t2Split[2])) {
                    return -1;
                } else if (Integer.parseInt(t1Split[2]) < Integer.parseInt(t2Split[2])) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public int getStartStopId() {
        return startStopId;
    }

    public void setStartStopId(int startStopId) {
        this.startStopId = startStopId;
    }

    public int getEndStopId() {
        return endStopId;
    }

    public void setEndStopId(int endStopId) {
        this.endStopId = endStopId;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
