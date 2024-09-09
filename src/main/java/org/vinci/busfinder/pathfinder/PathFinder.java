package org.vinci.busfinder.pathfinder;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class PathFinder {

    private int startStopId;
    private int endStopId;
    private String departureTime;

    @Autowired
    private GraphDataManger gdm;

    public PathFinder() {
    }

    public List<Integer> shortestPath() {

        if (gdm.getCachedStartStop().containsKey(startStopId)) {
            return gdm.getCachedStartStop().get(startStopId).get(endStopId);
        }

        int numNodes = gdm.getStopNetwork().keySet().stream().max(Integer::compare).get() + 1;
        int[] dist = new int[numNodes];

        HashMap<Integer, List<Integer>> paths = new HashMap<>();

        for (int i = 0; i < numNodes; i++) {
            paths.put(i, new ArrayList<>());
        }

        Boolean[] sptSet = new Boolean[numNodes];

        for (int n = 0; n < numNodes; n++) {
            dist[n] = Integer.MAX_VALUE;
            sptSet[n] = false;
        }

        dist[startStopId] = 0;

        for (int count = 0; count < numNodes; count++) {

            int u = minDistance(dist, sptSet, numNodes);

            sptSet[u] = true;

            for (int n = 0; n < numNodes; n++) {

                if (!sptSet[n] && (gdm.getStopNetwork().containsKey(u) && gdm.getStopNetwork().get(u).containsKey(n))
                        && dist[u] != Integer.MAX_VALUE
                        && dist[u] + 1 < dist[n]) {
                    dist[n] = dist[u] + 1;
                    List<Integer> aux = new ArrayList<>(paths.get(u));
                    aux.add(n);
                    paths.get(n).addAll(aux);
                }
            }
        }
        gdm.getCachedStartStop().put(startStopId, paths);
        return paths.get(endStopId);
    }

    private int minDistance(int[] dist, Boolean[] sptSet, int numNodes) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < numNodes; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        return min_index;
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
