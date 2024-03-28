package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {

  @Query("SELECT event FROM Event event WHERE event.published=true ORDER BY event.startDate ASC")
  List<Event> findByPublishedTrueOrderByStartDateAsc();
}
