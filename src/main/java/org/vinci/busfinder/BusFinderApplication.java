package org.vinci.busfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vinci.busfinder.pathfinder.GraphDataManger;
import org.vinci.busfinder.pathfinder.PathFinder;

@SpringBootApplication
public class BusFinderApplication {

    public static void main(String[] args) {
       SpringApplication.run(BusFinderApplication.class, args);
    }

    @Bean
    public PathFinder pathFinder() {
        return new PathFinder();
    }

}
