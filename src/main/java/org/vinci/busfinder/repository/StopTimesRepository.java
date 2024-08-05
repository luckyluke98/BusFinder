package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.StopTimes;
import org.vinci.busfinder.model.key.StopTimesKey;

@Repository
public interface StopTimesRepository extends JpaRepository<StopTimes, StopTimesKey> {
}
