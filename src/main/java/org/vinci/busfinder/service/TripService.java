package org.vinci.busfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vinci.busfinder.model.Trip;
import org.vinci.busfinder.repository.TripRepository;

import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public Trip findById(int id) {
        return tripRepository.findById(id).isPresent()
                ? tripRepository.findById(id).get()
                : null;
    }

    public List<Trip> findByRouteId(int route) {
        return null;
    }

    public List<Trip> findByTripHeadsign(String headsign) {
        return null;
    }





}
