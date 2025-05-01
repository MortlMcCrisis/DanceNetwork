package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.entity.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.NotFoundException;

public interface IEventService
{

  Event createEvent(Event event);

  Event getEvent(Long id);

  List<Event> getPublishedEvents(Optional<Integer> maxEntries, Optional<LocalDate> from);

  Event updateEvent(Event event) throws NotFoundException, IllegalAccessException;

  Event updateEventProperty(Long id, String property, String value)
      throws NotFoundException, IllegalAccessException;

  void publishEvent(long id) throws NotFoundException, IllegalAccessException;
}
