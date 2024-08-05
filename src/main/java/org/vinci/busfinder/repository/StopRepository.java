package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.Stop;

@Repository
public interface StopRepository extends JpaRepository<Stop, Integer> {
}
