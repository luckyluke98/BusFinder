package org.vinci.busfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vinci.busfinder.model.Route;
import org.vinci.busfinder.repository.RouteRepository;

import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Route findById(int id) {
        return routeRepository.findById(id).isPresent()
                ? routeRepository.findById(id).get()
                : null;
    }

    public List<Route> findByRouteShortName(String name) {
        return null;
    }

    public List<Route> findByRouteLongName(String name) {
        return null;
    }

}
