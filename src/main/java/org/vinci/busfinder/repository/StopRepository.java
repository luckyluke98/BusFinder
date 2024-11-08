package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.Stop;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Integer> {

    @Query("SELECT s.stopId FROM Stop s")
    public List<Integer> findAllStopId();

    @Query("SELECT s FROM Stop s WHERE s.stopId = ?1")
    public Stop findByStopId(int stopId);

}
