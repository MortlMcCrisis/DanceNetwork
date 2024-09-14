package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.dto.EventDTO;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.mapper.EventMapper;
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper;
import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.service.IEventService;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    event.setCreator(userService.getNonNullCurrentUser().uuid());
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
  public List<EventDTO> getPublishedEvents(
      Optional<Integer> maxEntries,
      Optional<LocalDate> fromDate){
    Pageable pageable;
    Sort sort = Sort.by(Direction.ASC, "startDate");
    if(maxEntries.isPresent()) {
      pageable = PageRequest.of( 0, maxEntries.get(), sort);
    }
    else{
      pageable = Pageable.unpaged(sort);
    }

    LocalDate from = fromDate.orElseGet(() -> LocalDate.now());

    return toDTOs(eventRepository.findByPublishedTrueAndStartDateAfter(from, pageable));
  }

  private List<EventDTO> toDTOs(List<Event> events){
    return events.stream()
        .map(event -> eventMapper.toDTO(event))
        .toList();
  }

  @Override
  public EventDTO updateEvent(EventDTO event) throws NotFoundException, IllegalAccessException
  {
    Event currentEvent = eventRepository.findById(event.id())
        .orElseThrow(NotFoundException::new);

    UUID uuid = userService.getCurrentUser().get().uuid();
    if(!currentEvent.getCreator().equals(uuid)){
      throw new IllegalAccessException();
    }

    currentEvent.setName(event.name());
    currentEvent.setEmail(event.email());
    currentEvent.setStartDate(event.startDate());
    currentEvent.setStartTime(event.startTime());
    currentEvent.setEndDate(event.endDate());
    currentEvent.setLocation(event.location());
    currentEvent.setWebsite(event.website());
    currentEvent.setProfileImage(event.profileImage());
    currentEvent.setBannerImage(event.bannerImage());
    currentEvent = eventRepository.save(currentEvent);

    return eventMapper.toDTO(currentEvent);
  }

  @Override
  public void publishEvent(long id) throws NotFoundException, IllegalAccessException
  {
    Event event = eventRepository.findById(id)
        .orElseThrow(NotFoundException::new);

    //TODO do this with an annotation on the endpoint
    if(!event.getCreator().equals(userService.getCurrentUser().get().uuid())){
      throw new IllegalAccessException();
    }

    event.setPublished(true);
    eventRepository.saveAndFlush(event);

    User currentUser = userService.getNonNullCurrentUser();

    newsfeedEntryService.createNewsfeedEntry(newsfeedEntryMapper.toDTO(
        newsfeedFactory.createEventPublishedNewsfeedEntry(currentUser, event)));
  }
}
