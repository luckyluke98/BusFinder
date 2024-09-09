package org.vinci.busfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.pathfinder.GraphDataManger;
import org.vinci.busfinder.repository.StopTimesRepository;

import java.util.List;
import java.util.Objects;

@Service
public class StopTimesService {

    @Autowired
    private StopTimesRepository stopTimesRepository;

}
