package org.vinci.busfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vinci.busfinder.pathfinder.PathFinder;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private PathFinder pathFinder;


    @GetMapping("algo")
    public List<Integer> test3(@RequestParam int i) {
        pathFinder.setStartStopId(4594);
        pathFinder.setEndStopId(4559);
        pathFinder.setDepartureTime("13:00:00");

        return pathFinder.shortestPath();
    }

}
