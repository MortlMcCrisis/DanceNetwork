package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.mapper.NewsfeedEntryMapper;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.repository.EventRepository;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import com.mortl.dancenetwork.service.IEventService;
import com.mortl.dancenetwork.service.INewsfeedEntryService;
import com.mortl.dancenetwork.service.IStripeService;
import com.mortl.dancenetwork.service.IUserService;
import com.mortl.dancenetwork.util.NewsfeedFactory;
import com.stripe.exception.StripeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ws.rs.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
public class EventServiceImpl implements IEventService
{

  private final EventRepository eventRepository;

  private final IUserService userService;

  private final INewsfeedEntryService newsfeedEntryService;

  private final IStripeService stripeService;

  private final NewsfeedFactory newsfeedFactory;

  //private final EventMapper eventMapper;

  private final NewsfeedEntryMapper newsfeedEntryMapper;

  private final TicketTypeRepository ticketTypeRepository;

  public EventServiceImpl(EventRepository eventRepository, IUserService userService,
      INewsfeedEntryService newsfeedEntryService, IStripeService stripeService,
      NewsfeedFactory newsfeedFactory, NewsfeedEntryMapper newsfeedEntryMapper,
      TicketTypeRepository ticketTypeRepository)
  {
    this.eventRepository = eventRepository;
    this.userService = userService;
    this.newsfeedEntryService = newsfeedEntryService;
    this.stripeService = stripeService;
    this.newsfeedFactory = newsfeedFactory;
    this.newsfeedEntryMapper = newsfeedEntryMapper;
    this.ticketTypeRepository = ticketTypeRepository;
  }

  @Override
  public Event createEvent(Event event)
  {
    event.setCreator(userService.getNonNullCurrentUser().getUuid());
    event.setCreatedAt(LocalDateTime.now());
    return eventRepository.saveAndFlush(event);
  }

  @Override
  public Event getEvent(Long id)
  {
    return eventRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found"));
  }

  @Override
  public List<Event> getPublishedEvents(
      Optional<Integer> maxEntries,
      Optional<LocalDate> fromDate)
  {
    Pageable pageable;
    Sort sort = Sort.by(Direction.ASC, "startDate");
    if (maxEntries.isPresent())
    {
      pageable = PageRequest.of(0, maxEntries.get(), sort);
    }
    else
    {
      pageable = Pageable.unpaged(sort);
    }

    LocalDate from = fromDate.orElseGet(() -> LocalDate.now());

    return eventRepository.findByPublishedTrueAndStartDateAfter(from, pageable);
  }

  @Override
  public Event updateEvent(Event event) throws NotFoundException, IllegalAccessException
  {
    Event currentEvent = eventRepository.findById(event.getId())
        .orElseThrow(NotFoundException::new);

    UUID uuid = userService.getCurrentUser().get().getUuid();
    if (!currentEvent.getCreator().equals(uuid))
    {
      throw new IllegalAccessException();
    }

    currentEvent.setName(event.getName());
    currentEvent.setEmail(event.getEmail());
    currentEvent.setStartDate(event.getStartDate());
    currentEvent.setStartTime(event.getStartTime());
    currentEvent.setEndDate(event.getEndDate());
    currentEvent.setLocation(event.getLocation());
    currentEvent.setWebsite(event.getWebsite());
    currentEvent.setProfileImage(event.getProfileImage());
    currentEvent.setBannerImage(event.getBannerImage());
    currentEvent = eventRepository.save(currentEvent);

    return currentEvent;
  }

  @Override
  public Event updateEventProperty(Long id, String property, String value)
      throws NotFoundException, IllegalAccessException
  {
    Event currentEvent = eventRepository.findById(id)
        .orElseThrow(NotFoundException::new);

    UUID uuid = userService.getCurrentUser().get().getUuid();
    if (!currentEvent.getCreator().equals(uuid))
    {
      throw new IllegalAccessException();
    }

    switch (property)
    {
      case "name" -> currentEvent.setName(value);
      case "profileImage" -> currentEvent.setProfileImage(value);
      case "bannerImage" -> currentEvent.setBannerImage(value);
      case "location" -> currentEvent.setLocation(value);
      case "website" -> currentEvent.setWebsite(value);
      case "email" -> currentEvent.setEmail(value);
      case "startDate" -> currentEvent.setStartDate(LocalDate.parse(value));
      case "startTime" -> currentEvent.setStartTime(LocalTime.parse(value));
      case "endDate" -> currentEvent.setEndDate(LocalDate.parse(value));
      case "published" -> currentEvent.setPublished(Boolean.parseBoolean(value));
      default -> throw new IllegalArgumentException("Invalid property: " + property);
    }

    currentEvent = eventRepository.save(currentEvent);

    return currentEvent;
  }

  @Override
  public void publishEvent(long id) throws NotFoundException, IllegalAccessException
  {
    List<TicketType> ticketTypes = ticketTypeRepository.findByEventId(id);
    if (ticketTypes.isEmpty())
    {
      throw new IllegalStateException("An Event without tickets can not be published");
    }

    Event event = eventRepository.findById(id)
        .orElseThrow(NotFoundException::new);

    //TODO do this with an annotation on the endpoint
    if (!event.getCreator().equals(userService.getCurrentUser().get().getUuid()))
    {
      throw new IllegalAccessException();
    }

    event.setPublished(true);
    eventRepository.saveAndFlush(event);

    try
    {
      stripeService.activateTickets(ticketTypes);
    }
    catch (StripeException e)
    {
      throw new RuntimeException(e);
    }

    User currentUser = userService.getNonNullCurrentUser();

    newsfeedEntryService.createNewsfeedEntry(
        newsfeedFactory.createEventPublishedNewsfeedEntry(currentUser, event));
  }
}
