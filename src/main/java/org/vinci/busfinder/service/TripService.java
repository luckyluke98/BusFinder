package org.vinci.busfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vinci.busfinder.repository.TripRepository;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

}
