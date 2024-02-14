package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.client.UserClient;
import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.dto.NewsfeedEntryDTO;
import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.NewsfeedEntry;
import com.mortl.dancenetwork.repository.EventRepository;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventService {

  private final EventRepository eventRepository;

  private final UserClient userClient;

  public EventDTO createEvent(EventDTO eventDTO)
  {
    User currentUser = userClient.getCurrentUser();
    Event savedEvent = eventRepository.saveAndFlush(eventDTO.toModel(currentUser.uuid()));

    return EventDTO.fromModel(savedEvent);
  }

  public EventDTO getEvent(Long id){
    Optional<Event> event = eventRepository.findById(id);
    if(!event.isPresent()){
      throw new NotFoundException("Event with id " + id + " not found");
    }
    return EventDTO.fromModel(event.get());
  }

  public EventDTO updateEvent(EventDTO event) throws NotFoundException{
      //TODO only the owner of a user should be able to update an entry
      Event currentEvent = eventRepository.findById(event.id())
          .orElseThrow(NotFoundException::new);

      currentEvent.setName(event.name());
      currentEvent.setMail(event.mail());
      currentEvent.setDate(event.date());
      currentEvent.setAddress(event.address());
      currentEvent.setUrl(event.url());
      currentEvent = eventRepository.save(currentEvent);

      return EventDTO.fromModel(
          currentEvent);
    }
  }
