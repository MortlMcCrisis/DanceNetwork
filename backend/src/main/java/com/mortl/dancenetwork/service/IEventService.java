package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.EventDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.NotFoundException;
import org.springframework.data.domain.Sort;

public interface IEventService {
  
  EventDTO createEvent(EventDTO eventDTO);

  EventDTO getEvent(Long id);

  List<EventDTO> getPublishedEvents(Optional<Integer> maxEntries, Optional<LocalDate> from);

  EventDTO updateEvent(EventDTO event) throws NotFoundException, IllegalAccessException;

  void publishEvent(long id) throws NotFoundException, IllegalAccessException;
}
