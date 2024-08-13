package org.vinci.busfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vinci.busfinder.model.Stop;
import org.vinci.busfinder.repository.StopRepository;

import java.util.List;

@Service
public class StopService {

    @Autowired
    private StopRepository stopRepository;

    public List<Stop> findAll() {
        return stopRepository.findAll();
    }

    public Stop findById(int id) {
        return stopRepository.findById(id).isPresent()
                ? stopRepository.findById(id).get()
                : null;
    }

    public List<Stop> findByStopName(String name) {
        return null;
    }


}
