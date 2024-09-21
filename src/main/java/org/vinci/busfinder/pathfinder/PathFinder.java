package org.vinci.busfinder.pathfinder;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class PathFinder {

    @Autowired
    private GraphDataManger gdm;

    public PathFinder() {}

    public List<Integer> findPaths(int startStopId, int endStopId, String departureTime) {
        List<Integer> sp = shortestPath(startStopId, endStopId);

        if (Objects.isNull(sp)) {
            return null;
        }

        HashMap<Integer, HashMap<Integer, List<Integer>>> stg = new HashMap<>();
        sp.forEach(stop -> {
            stg.put(stop, new HashMap<>());
        });

        for (int i = 0; i < sp.size() - 1; i++) {
            List<Integer> routeIdsFromStop = new ArrayList<>();

            gdm.getStopFromGraph(sp.get(i)).forEach((id, list) -> {
                list.forEach(r -> {
                    if (!routeIdsFromStop.contains(r)) {
                        routeIdsFromStop.add(r);
                    }
                });
            });

            HashMap<Integer, List<Integer>> map = stg.get(sp.get(i));

            for (int j = i + 1; j < sp.size(); j++) {
                map.put(sp.get(j), new ArrayList<>());

                for (Integer r : routeIdsFromStop) {
                    if (gdm.leadsTo(r, sp.get(i), sp.get(j))) {
                        map.get(sp.get(j)).add(r);
                    }
                }

            }
        }

        System.out.println("**********************");



        stg.forEach((k,v) -> {
            v.forEach((k1,v1) -> {
                System.out.println(k+": "+k1+": "+v1);
            });

        });

        return sp;
    }

    public List<Integer> shortestPath(int startStopId, int endStopId) { //Dijkstra based
        //If the startStop is in cache retrieve that
        if (gdm.isCached(startStopId)) {
            return gdm.getCachedStartStop(startStopId, endStopId);
        }

        int numNodes = gdm.getNumStopNodes();
        int[] dist = new int[numNodes];

        HashMap<Integer, List<Integer>> paths = new HashMap<>();

        //Initialize for each stop the corresponding shortest path list
        for (int i = 0; i < numNodes; i++) {
            paths.put(i, new ArrayList<>());
            if (i == endStopId) {
                paths.get(i).add(startStopId);
            }
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
                if (!sptSet[n] && gdm.areAdjacentStops(u, n)
                        && dist[u] != Integer.MAX_VALUE
                        && dist[u] + 1 < dist[n]) {
                    dist[n] = dist[u] + 1;
                    List<Integer> aux = new ArrayList<>(paths.get(u));
                    aux.add(n);
                    paths.get(n).addAll(aux);
                }
            }
        }
        gdm.cacheStartStop(startStopId, paths);
        return paths.get(endStopId);
    }

    private int minDistance(int[] dist, Boolean[] sptSet, int numNodes) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < numNodes; v++)
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        return min_index;
    }

}
