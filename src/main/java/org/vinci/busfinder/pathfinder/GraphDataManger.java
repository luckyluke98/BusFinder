package org.vinci.busfinder.pathfinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vinci.busfinder.repository.RouteRepository;
import org.vinci.busfinder.repository.StopRepository;
import org.vinci.busfinder.repository.StopTimesRepository;
import org.vinci.busfinder.repository.TripRepository;

import java.util.*;

@Component
public class GraphDataManger {

    private static final Logger log = LoggerFactory.getLogger(GraphDataManger.class);

    private static GraphDataManger instance;

    private HashMap<Integer, List<Integer>> tripsPerRoute;
    private HashMap<Integer, List<Integer>> routeStop;

    private GraphModel tripGraph;

    @Autowired
    private StopTimesRepository stopTimesRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RouteRepository routeRepository;

    private GraphDataManger() {
        tripsPerRoute = new HashMap<>();
        routeStop = new HashMap<>();
        tripGraph = new GraphModel();
    }

    public void load() {

        tripsPerRoute.clear();
        routeStop.clear();
        tripGraph.clear();

        List<Integer> stopIds = stopRepository.findAllStopId();
        List<Integer> routeIds = routeRepository.findAllRouteId();

        for (Integer routeId : routeIds) {
            List<Integer> tripIdsByRoute = tripRepository.findTripIdsByRouteId(routeId);
            tripsPerRoute.put(routeId, tripIdsByRoute);

            int trip = tripIdsByRoute.getFirst();
            List<Integer> stops = stopTimesRepository.findStopIdsByTripId(trip);
            routeStop.put(routeId, stops);
        }

        stopIds.forEach(stop -> tripGraph.addNode(stop));

        for (Map.Entry<Integer, List<Integer>> entry : routeStop.entrySet()) {
            Integer routeId = entry.getKey();
            List<Integer> stops = entry.getValue();
            for (int i = 0; i < stops.size(); i++) {
                if (i + 1 < stops.size()) {
                    tripGraph.addEdge(stops.get(i), stops.get(i + 1), routeId);
                }
            }
        }

        tripGraph.printGraph();
    }

    public HashMap<Integer, List<Integer>> getStop(int stop){
        return tripGraph.getNode(stop);
    }

    public static GraphDataManger getInstance() {
        if (instance == null) {
            instance = new GraphDataManger();
        }
        return instance;
    }

    public boolean leadsTo(int route, int src, int dst) {
        List<Integer> routeStop = getRouteStop().get(route);
        if (routeStop.contains(dst)) {
            return routeStop.indexOf(src) <= routeStop.indexOf(dst);
        }
        return false;
    }

    public HashMap<Integer, List<Integer>> getTripsPerRoute() {
        return tripsPerRoute;
    }

    public HashMap<Integer, List<Integer>> getRouteStop() {
        return routeStop;
    }

    public List<Integer> shortestPath(int src, int dst) {
        return tripGraph.shortestPath(src, dst);
    }
}
