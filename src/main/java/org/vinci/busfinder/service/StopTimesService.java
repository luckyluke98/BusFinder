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
    GraphDataManger gdm;

    @Autowired
    private StopTimesRepository stopTimesRepository;

    public List<Integer> findTripsIdByStopId(int stopId) {
        if (!gdm.getInMemory()) {
            return stopTimesRepository.findTripIdByStopId(stopId);
        }
        return gdm.getTripsPerStop().get(stopId);
    }

    public List<StopTimes> findByTripId(int tripId) {
        if (!gdm.getInMemory()) {
            return stopTimesRepository.findByTripId(tripId);
        }
        return gdm.getRouteStop().get(tripId);
    }


    public int getSeqNum(int stopId, List<StopTimes> stopTimes) {

        StopTimes stopTime = stopTimes.stream()
                .filter(st -> st.getId().getStopId() == stopId)
                .findFirst()
                .orElse(null);

        return Objects.nonNull(stopTime)
                ? stopTime.getId().getStopSeq()
                : -1;
    }

    public boolean isLastStop(int stopId, List<StopTimes> trip) {
        return trip.getLast().getId().getStopId() == stopId;
    }

    public boolean nextStop(int stopId, List<StopTimes> trip) {
        int seq = getSeqNum(stopId, trip);
        if (seq > -1) {
            int last = trip.getLast().getId().getStopSeq();

            return seq + 1 <= last;
        }
        return false;

    }

    public StopTimes getStopTimeByNumSeq(int numSeq, List<StopTimes> trip) {
        return trip.stream()
                .filter(st -> st.getId().getStopSeq() == numSeq)
                .findFirst()
                .orElse(null);

    }

}
