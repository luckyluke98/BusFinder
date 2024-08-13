package org.vinci.busfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.repository.StopTimesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class StopTimesService {

    HashMap<Integer, List<Integer>> cacheTripsId = new HashMap<>();
    HashMap<Integer, List<StopTimes>> cacheStopTimes = new HashMap<>();

    @Autowired
    private StopTimesRepository stopTimesRepository;

    public List<Integer> findTripsIdByStopId(int stopId) {
        if (!cacheTripsId.containsKey(stopId)) {
            List<Integer> res = stopTimesRepository.findTripIdByStopId(stopId);
            cacheTripsId.put(stopId, res);
            return res;
        }
        return cacheTripsId.get(stopId);
    }

    public List<StopTimes> findByTripId(int tripId) {
        //Ordinato per seq number
        if (!cacheStopTimes.containsKey(tripId)) {
            return null;
        }

        return cacheStopTimes.get(tripId);
    }

    public int getSeqNum(int stopId, int tripId) {
        List<StopTimes> stopTimes = findByTripId(tripId);

        StopTimes stopTime = stopTimes.stream()
                .filter(st -> st.getId().getStopId() == stopId)
                .findFirst()
                .orElse(null);

        return Objects.nonNull(stopTime)
                ? stopTime.getId().getStopSeq()
                : -1;
    }

}
