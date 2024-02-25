package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.EventDTO;
import javax.ws.rs.NotFoundException;

public interface IEventService {
  
  EventDTO createEvent(EventDTO eventDTO);

  EventDTO getEvent(Long id);

  EventDTO updateEvent(EventDTO event) throws NotFoundException;

  void publishEvent(long id);
}
