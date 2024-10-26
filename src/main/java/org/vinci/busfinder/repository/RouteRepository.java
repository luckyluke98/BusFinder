package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.Route;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    @Query("SELECT r.routeId FROM Route r")
    public List<Integer> findAllRouteId();

    public Route findByRouteId(int routeId);
}
