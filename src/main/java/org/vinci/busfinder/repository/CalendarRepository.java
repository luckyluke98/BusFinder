package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.dto.CalendarDto;
import org.vinci.busfinder.model.Calendar;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    @Modifying
    @Query(value = "INSERT INTO calendar (service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date)" +
            "VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)", nativeQuery = true)
    void saveRaw(String serviceId, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday,
                 boolean saturday, boolean sunday, String startDate, String endDate);

    default void saveAllRaw(List<CalendarDto> calendars) {
        calendars.forEach(calendar -> {
            saveRaw(calendar.getServiceId(), calendar.isMonday(), calendar.isTuesday(), calendar.isWednesday(),
                    calendar.isThursday(), calendar.isFriday(), calendar.isSaturday(), calendar.isSunday(),
                    calendar.getStartDate(), calendar.getEndDate());
        });
    }

}
