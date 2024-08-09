package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.vinci.busfinder.model.Calendar;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
}
