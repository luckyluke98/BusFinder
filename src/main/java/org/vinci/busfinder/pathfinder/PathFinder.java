package org.vinci.busfinder.pathfinder;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vinci.busfinder.model.Route;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.repository.RouteRepository;
import org.vinci.busfinder.repository.StopTimesRepository;

import java.util.*;

@Component
public class PathFinder {

    @Autowired
    private GraphDataManger gdm;

    @Autowired
    private StopTimesRepository stopTimesRepository;

    @Autowired
    private RouteRepository routeRepository;

    public PathFinder() {}

    public List<Integer> findPaths(int startStopId, int endStopId, String departureTime) {
        List<Integer> sp = gdm.shortestPath(startStopId, endStopId);
        sp.addFirst(startStopId);

        GraphModel stg = initSTG(sp);
        stg.printGraph();

        List<Integer> stgShortestPath = stg.shortestPath(startStopId, endStopId);
        List<List<Integer>> linkTrips = new ArrayList<>();

        for (int i = 0; i < stgShortestPath.size() - 1; i++) {
            linkTrips.add(stg.getNode(stgShortestPath.get(i)).get(stgShortestPath.get(i + 1)));
        }

        TripPath out = new TripPath();

        int stop = 0;
        String depTime = departureTime;
        for(List<Integer> linkTrip : linkTrips) {

            StopTimes min = stopTimesRepository.findDepartureTripsByRouteByStopByTime(linkTrip.getFirst(), stgShortestPath.get(stop), depTime);
            int minRouteId = linkTrip.getFirst();

            for (int l: linkTrip) {
                StopTimes att = stopTimesRepository.findDepartureTripsByRouteByStopByTime(l, stgShortestPath.get(stop), depTime);

                if (Objects.nonNull(att) && StopTimes.compareTime(min.getDepartureTime(), att.getDepartureTime()) < 0) {
                    min = att;
                    minRouteId = l;
                }
            }

            Route minRoute = routeRepository.findById(minRouteId).get();
            StopTimes dst = stopTimesRepository.findStopTimesByStopIdByTripId(stgShortestPath.get(stop + 1), min.getId().getTripId());
            depTime = dst.getArrivalTime();
            out.addStep(minRoute, min, dst);

            stop++;
        }

        out.getSteps().forEach(s -> {
            System.out.println(s.getRoute().getRouteShortName() + ":" + s.getStopTimesPair().getSrc() + ", " + s.getStopTimesPair().getDst());
            System.out.println();
        });

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
