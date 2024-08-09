package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Integer> {
}
