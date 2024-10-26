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
    public List<Integer> test3(@RequestParam int start, @RequestParam int end, @RequestParam String deptime) {
        return pathFinder.findPaths(start, end, deptime);
    }

}
