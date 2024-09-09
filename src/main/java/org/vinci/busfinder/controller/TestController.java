package org.vinci.busfinder.controller;

import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.pathfinder.Path;
import org.vinci.busfinder.pathfinder.PathFinder;
import org.vinci.busfinder.service.StopTimesService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private PathFinder pathFinder;


    @GetMapping("algo")
    public List<Pair<Integer, List<Integer>>> test3() {
        pathFinder.setStartStopId(4593);
        pathFinder.setEndStopId(4559);
        pathFinder.setDepartureTime("13:00:00");

        return pathFinder.findAllPaths();
    }

}
