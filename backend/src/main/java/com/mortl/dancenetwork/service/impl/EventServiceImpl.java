package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.mapper.EventMapper;
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper;
import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.service.IEventService;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

  private final INewsfeedEntryService newsfeedEntryService;

  private final NewsfeedFactory newsfeedFactory;

  private final EventMapper eventMapper;

  private final NewsfeedEntryMapper newsfeedEntryMapper;

  @Override
  public EventDTO createEvent(EventDTO eventDTO)
  {
    Event event = eventMapper.toModel(eventDTO);
    event.setCreator(userService.getCurrentUser().uuid());
    Event savedEvent = eventRepository.saveAndFlush(event);
    return eventMapper.toDTO(savedEvent);  }

  @Override
  public EventDTO getEvent(Long id)
  {
    Optional<Event> event = eventRepository.findById(id);
    if(!event.isPresent()){
      throw new NotFoundException("Event with id " + id + " not found");
    }
    return eventMapper.toDTO(event.get());
  }

  @Override
  public List<EventDTO> getAllPublishedEvents(){
    return toDTOs(eventRepository.findByPublishedTrueOrderByStartDateAsc());
  }

  private List<EventDTO> toDTOs(List<Event> events){
    return events.stream()
        .map(event -> eventMapper.toDTO(event))
        .toList();
  }

  @Override
  public EventDTO updateEvent(EventDTO event) throws NotFoundException
  {
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

    return eventMapper.toDTO(currentEvent);
  }

  @Override
  public void publishEvent(long id)
  {
    //TODO only the owner of a user should be able to publish an entry
    Event event = eventRepository.findById(id)
        .orElseThrow(NotFoundException::new);

    event.setPublished(true);
    eventRepository.saveAndFlush(event);

    User currentUser = userService.getCurrentUser();

    newsfeedEntryService.createNewsfeedEntry(newsfeedEntryMapper.toDTO(
        newsfeedFactory.createEventPublishedNewsfeedEntry(currentUser, event)));
  }
}
