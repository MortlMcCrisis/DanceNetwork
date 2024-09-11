package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.TicketType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

  @Query("SELECT event FROM Event event WHERE event.published=true AND event.startDate > :fromDate")
  List<Event> findByPublishedTrueAndStartDateAfter(@Param("fromDate") LocalDate fromDate, Pageable pageable);
}
