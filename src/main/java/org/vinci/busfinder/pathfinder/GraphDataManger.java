package org.vinci.busfinder.pathfinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.vinci.busfinder.model.Route;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.repository.RouteRepository;
import org.vinci.busfinder.repository.StopRepository;
import org.vinci.busfinder.repository.StopTimesRepository;
import org.vinci.busfinder.repository.TripRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GraphDataManger {

    private static final Logger log = LoggerFactory.getLogger(GraphDataManger.class);

    private static GraphDataManger instance;

    private HashMap<Integer, List<Integer>> tripsPerStop;
    private HashMap<Integer, List<Integer>> tripsPerRoute;
    private HashMap<Integer, List<StopTimes>> routeStop;
    private HashMap<Integer, List<Integer>> stopNetwork;

    private Boolean inMemory;

    @Autowired
    private StopTimesRepository stopTimesRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private RouteRepository routeRepository;

    private GraphDataManger() {
        tripsPerStop = new HashMap<>();
        tripsPerRoute = new HashMap<>();
        routeStop = new HashMap<>();
        stopNetwork = new HashMap<>();
        inMemory = false;
    }

    public void load() {
        log.info("==========================================================");
        log.info("Loading graph data manager ... ");
        tripsPerStop.clear();
        tripsPerRoute.clear();
        routeStop.clear();
        stopNetwork.clear();

        List<Integer> stopIds = stopRepository.findAllStopId();
        List<Integer> routeIds = routeRepository.findAllRouteId();

        log.info("Loading tripsPerRoute and routeStop index ...");
        for (Integer routeId : routeIds) {
            List<Integer> tripIdsByRoute = tripRepository.findTripIdsByRouteId(routeId);
            tripsPerRoute.put(routeId, tripIdsByRoute);

            int trip = tripIdsByRoute.getFirst();
            List<StopTimes> stopTimes = stopTimesRepository.findByTripId(trip);
            routeStop.put(routeId, stopTimes);
        }

        stopIds.forEach(stop -> {
            stopNetwork.put(stop, new ArrayList<Integer>());
        });

        routeStop.forEach((k,v) -> {
            for (int i = 0; i < v.size(); i++) {
                if (i + 1 < v.size()) {
                    if (!stopNetwork.get(v.get(i).getId().getStopId()).contains(v.get(i+1).getId().getStopId())) {
                        stopNetwork.get(v.get(i).getId().getStopId()).add(v.get(i+1).getId().getStopId());
                    }
                }
            }
        });

        log.info("Loading ended!");

        stopNetwork.forEach((k,v) -> {
            System.out.print(k+": ");
            v.forEach(s -> {
                System.out.print(s+" ");
            });
            System.out.println();
        });

        inMemory = true;

    }

    public static GraphDataManger getInstance() {
        if (instance == null) {
            instance = new GraphDataManger();
        }
        return instance;
    }

    public HashMap<Integer, List<Integer>> getTripsPerStop() {
        return tripsPerStop;
    }

    public HashMap<Integer, List<Integer>> getTripsPerRoute() {
        return tripsPerRoute;
    }

    public HashMap<Integer, List<StopTimes>> getRouteStop() {
        return routeStop;
    }

    public Boolean getInMemory() {
        return inMemory;
    }

    public HashMap<Integer, List<Integer>> getStopNetwork() {
        return stopNetwork;
    }
}
