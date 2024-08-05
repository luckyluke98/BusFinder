package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.dto.AgencyDto;
import org.vinci.busfinder.model.Agency;

import java.util.List;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Integer> {

    @Modifying
    @Query(value = "INSERT INTO agency (agency_id,agency_name,agency_url,agency_time_zone,agency_lang,agency_phone,agency_fare_url)" +
            "VALUES (?1,?2,?3,?4,?5,?6,?7)", nativeQuery = true)
    void saveRaw(String agencyId, String agencyName, String agencyUrl, String agencyTimeZone,
                 String agencyLang, String agencyPhone, String agencyFareUrl);

    default void saveAllRaw(List<AgencyDto> agencies) {
        agencies.forEach(agency -> {
            saveRaw(agency.getAgencyId(), agency.getAgencyName(), agency.getAgencyUrl(), agency.getAgencyTimeZone(),
                    agency.getAgencyLang(), agency.getAgencyPhone(), agency.getAgencyFareUrl());
        });
    }

}
