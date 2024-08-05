package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.dto.RouteDto;
import org.vinci.busfinder.model.Route;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    @Modifying
    @Query(value = "INSERT INTO routes (route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color)" +
            "VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9)", nativeQuery = true)
    void saveRaw(int routeId, String agencyId, String routeShortName, String routeLongName, String routeDesc, String routeType,
                 String routeUrl, String routeColor, String routeTextColor);

    default void saveAllRaw(List<RouteDto> routes) {
        routes.forEach(route -> {
            saveRaw(route.getRouteId(), route.getAgencyId(), route.getRouteShortName(), route.getRouteLongName(),
                    route.getRouteDesc(), route.getRouteType(), route.getRouteUrl(), route.getRouteColor(), route.getRouteTextColor());
        });
    }

}
