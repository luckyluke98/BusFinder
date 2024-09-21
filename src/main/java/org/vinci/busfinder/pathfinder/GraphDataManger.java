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
    private HashMap<Integer, HashMap<Integer, List<Integer>>> stopNetwork;

    private HashMap<Integer, HashMap<Integer, List<Integer>>> cachedStartStop;

    private int numStopNodes;

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
        stopNetwork = new HashMap<>();
        cachedStartStop = new HashMap<>();
    }

    public void load() {

        tripsPerRoute.clear();
        routeStop.clear();
        stopNetwork.clear();

        List<Integer> stopIds = stopRepository.findAllStopId();
        List<Integer> routeIds = routeRepository.findAllRouteId();

        for (Integer routeId : routeIds) {
            List<Integer> tripIdsByRoute = tripRepository.findTripIdsByRouteId(routeId);
            tripsPerRoute.put(routeId, tripIdsByRoute);

            int trip = tripIdsByRoute.getFirst();
            List<Integer> stops = stopTimesRepository.findStopIdsByTripId(trip);
            routeStop.put(routeId, stops);
        }

        for (Integer stop : stopIds) {
            stopNetwork.put(stop, new HashMap<>());
        }

        for (Map.Entry<Integer, List<Integer>> entry : routeStop.entrySet()) {
            Integer routeId = entry.getKey();
            List<Integer> stops = entry.getValue();
            for (int i = 0; i < stops.size(); i++) {
                if (i + 1 < stops.size()) {
                    HashMap<Integer, List<Integer>> map = stopNetwork.get(stops.get(i));
                    if (!map.containsKey(stops.get(i + 1))) {
                        map.put(stops.get(i + 1), new ArrayList<>());
                    }

                    map.get(stops.get(i + 1)).add(routeId);
                }
            }
        }

        numStopNodes = stopNetwork.keySet().stream().max(Integer::compare).get() + 1;

        printGraph();
    }

    public boolean isCached(int stop) {
        return cachedStartStop.containsKey(stop);
    }

    public List<Integer> getCachedStartStop(int startStop, int endStop) {
        return isCached(startStop)
                ? cachedStartStop.get(startStop).get(endStop)
                : new ArrayList<>();
    }

    public void cacheStartStop(int startStop, HashMap<Integer, List<Integer>> paths) {
        cachedStartStop.put(startStop, paths);
    }

    public boolean containsStop(int stop) {
        return stopNetwork.containsKey(stop);
    }

    public HashMap<Integer, List<Integer>> getStopFromGraph(int stop){
        return stopNetwork.get(stop);
    }

    public boolean areAdjacentStops(int startStop, int endStop) {
        return containsStop(startStop) && getStopFromGraph(startStop).containsKey(endStop);
    }

    public int getNumStopNodes() {
        return numStopNodes;
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

    public void printGraph(){
        stopNetwork.forEach((k,v) -> {
            v.forEach((k1,v1) -> {
                System.out.println(k+": "+k1+": "+v1);
            });

        });
    }

    public HashMap<Integer, List<Integer>> getTripsPerRoute() {
        return tripsPerRoute;
    }

    public HashMap<Integer, List<Integer>> getRouteStop() {
        return routeStop;
    }

    public HashMap<Integer, HashMap<Integer, List<Integer>>> getStopNetwork() {
        return stopNetwork;
    }

    public HashMap<Integer, HashMap<Integer, List<Integer>>> getCachedStartStop() {
        return cachedStartStop;
    }

    public void setCachedStartStop(HashMap<Integer, HashMap<Integer, List<Integer>>> cachedStartStop) {
        this.cachedStartStop = cachedStartStop;
    }
}
