package org.vinci.busfinder.pathfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.repository.StopTimesRepository;

import java.util.*;

public class PathFinder {

    @Autowired
    private GraphDataManger gdm;

    @Autowired
    private StopTimesRepository stopTimesRepository;

    public PathFinder() {}

    public List<Integer> findPaths(int startStopId, int endStopId, String departureTime) {
        List<Integer> sp = gdm.shortestPath(startStopId, endStopId);

        if (Objects.isNull(sp)) {
            return null;
        }

        GraphModel stg = initSTG(sp);

        stg.printGraph();

        List<Integer> stgShortestPath = stg.shortestPath(startStopId, endStopId);
        List<List<Integer>> linkTrips = new ArrayList<>();

        for (int i = 0; i < stgShortestPath.size()-1; i++) {
            linkTrips.add(stg.getNode(stgShortestPath.get(i)).get(stgShortestPath.get(i+1)));
        }

        System.out.println(stgShortestPath);
        System.out.println(linkTrips);

        System.out.println(stopTimesRepository.findDepartureTripsByRouteByStopByTime(4,4639, "06:00:00"));

        int stop = 0;
        String depTime = departureTime;
        for(List<Integer> linkTrip : linkTrips) {

            StopTimes min = stopTimesRepository.findDepartureTripsByRouteByStopByTime(linkTrip.getFirst(), stgShortestPath.get(stop), depTime);
            for (int l: linkTrip) {
                StopTimes att = stopTimesRepository.findDepartureTripsByRouteByStopByTime(l, stgShortestPath.get(stop), depTime);
                if (StopTimes.compareTime(min.getDepartureTime(), att.getDepartureTime()) < 0)
                    min = att;
            }

        }


        return sp;
    }


    private GraphModel initSTG(List<Integer> sp) {
        GraphModel stg = new GraphModel();
        sp.forEach(stg::addNode);

        for (int i = 0; i < sp.size() - 1; i++) {
            List<Integer> routeIdsFromStop = new ArrayList<>();

            gdm.getStop(sp.get(i)).forEach((id, list) -> {
                for (Integer r : list) {
                    if (!routeIdsFromStop.contains(r))
                        routeIdsFromStop.add(r);
                }
            });

            for (int j = i + 1; j < sp.size(); j++)
                for (Integer r : routeIdsFromStop)
                    if (gdm.leadsTo(r, sp.get(i), sp.get(j)))
                        stg.addEdge(sp.get(i), sp.get(j), r);
        }
        return stg;
    }

}
