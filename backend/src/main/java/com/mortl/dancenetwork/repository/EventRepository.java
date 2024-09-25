package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.entity.Event;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {

  @Query("SELECT event FROM Event event WHERE event.published=true AND event.startDate > :fromDate")
  List<Event> findByPublishedTrueAndStartDateAfter(LocalDate fromDate, Pageable pageable);

  @Query("SELECT e.createdAt FROM Event e WHERE e.id = :eventId")
  LocalDateTime findCreatedAtByEventId(long eventId);
}
