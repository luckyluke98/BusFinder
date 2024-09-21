package org.vinci.busfinder.pathfinder;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class PathFinder {

    @Autowired
    private GraphDataManger gdm;

    public PathFinder() {}

    public List<Integer> findPaths(int startStopId, int endStopId, String departureTime) {
        List<Integer> sp = gdm.shortestPath(startStopId, endStopId);

        if (Objects.isNull(sp)) {
            return null;
        }

        GraphModel stg = new GraphModel();
        sp.forEach(stg::addNode);

        for (int i = 0; i < sp.size() - 1; i++) {
            List<Integer> routeIdsFromStop = new ArrayList<>();

            gdm.getStop(sp.get(i)).forEach((id, list) -> {
                list.forEach(r -> {
                    if (!routeIdsFromStop.contains(r)) {
                        routeIdsFromStop.add(r);
                    }
                });
            });

            for (int j = i + 1; j < sp.size(); j++) {
                for (Integer r : routeIdsFromStop) {
                    if (gdm.leadsTo(r, sp.get(i), sp.get(j))) {
                        stg.addEdge(sp.get(i), sp.get(j), r);
                    }
                }

            }
        }

        System.out.println("**********************");

        stg.printGraph();

        return sp;
    }
}
