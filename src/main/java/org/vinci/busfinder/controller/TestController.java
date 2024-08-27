package org.vinci.busfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.pathfinder.Path;
import org.vinci.busfinder.pathfinder.PathFinder;
import org.vinci.busfinder.service.StopTimesService;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private PathFinder pathFinder;

    @Autowired
    private StopTimesService stopTimesService;

    @GetMapping("stoptimes/{id}")
    public List<Integer> test(@PathVariable int id) {
        return stopTimesService.findTripsIdByStopId(id);
    }

    @GetMapping("trip/{id}")
    public List<StopTimes> test2(@PathVariable int id) {
        return stopTimesService.findByTripId(id);
    }

    @GetMapping("algo")
    public List<Path> test3() {
        pathFinder.setStartStopId(4593);
        pathFinder.setEndStopId(4559);
        pathFinder.setDepartureTime("13:00:00");

        return pathFinder.findAllPaths();
    }

}
