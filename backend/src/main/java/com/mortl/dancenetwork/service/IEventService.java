package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.EventDTO;
import java.util.List;
import javax.ws.rs.NotFoundException;

public interface IEventService {
  
  EventDTO createEvent(EventDTO eventDTO);

  EventDTO getEvent(Long id);

  List<EventDTO> getAllPublishedEvents();

  EventDTO updateEvent(EventDTO event) throws NotFoundException, IllegalAccessException;

  void publishEvent(long id) throws NotFoundException, IllegalAccessException;
}
