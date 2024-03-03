package com.mortl.dancenetwork.repository;

import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {

  //TODO sort by creation date
  //TODO change here and in the Newsletter Repository to fetch all entries at once
  @Query("SELECT event.id FROM Event event WHERE event.published=true")
  List<Long> findAllPublishedIds();
}
