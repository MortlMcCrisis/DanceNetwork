package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.service.IEventService;
import com.mortl.dancenetwork.service.IUserService;
import java.util.Optional;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements IEventService {

  private final EventRepository eventRepository;

  private final IUserService userService;

  @Override
  public EventDTO createEvent(EventDTO eventDTO)
  {
    User currentUser = userService.getCurrentUser();
    Event savedEvent = eventRepository.saveAndFlush(eventDTO.toModel(currentUser.uuid()));

    return EventDTO.fromModel(savedEvent);
  }

  @Override
  public EventDTO getEvent(Long id){
    Optional<Event> event = eventRepository.findById(id);
    if(!event.isPresent()){
      throw new NotFoundException("Event with id " + id + " not found");
    }
    return EventDTO.fromModel(event.get());
  }

  @Override
  public EventDTO updateEvent(EventDTO event) throws NotFoundException{
    //TODO only the owner of a user should be able to update an entry
    Event currentEvent = eventRepository.findById(event.id())
        .orElseThrow(NotFoundException::new);

    currentEvent.setName(event.name());
    currentEvent.setEmail(event.email());
    currentEvent.setStartDate(event.startDate());
    currentEvent.setEndDate(event.endDate());
    currentEvent.setLocation(event.location());
    currentEvent.setWebsite(event.website());
    currentEvent = eventRepository.save(currentEvent);

    return EventDTO.fromModel(
        currentEvent);
  }

  @Override
  public void publishEvent(long id) {
    //TODO only the owner of a user should be able to publish an entry
    Event event = eventRepository.findById(id)
        .orElseThrow(NotFoundException::new);

    event.setPublished(true);
    eventRepository.saveAndFlush(event);
  }
}
