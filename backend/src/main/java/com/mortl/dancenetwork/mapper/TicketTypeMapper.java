package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.repository.EventRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TicketTypeMapper
{

  @Autowired
  protected EventRepository eventRepository;

  @Mapping(target = "event", source = "eventId", qualifiedByName = "eventIdToEvent")
  public abstract TicketType toEntity(TicketTypeDTO dto);

  @Mapping(target = "eventId", source = "event", qualifiedByName = "eventToEventId")
  public abstract TicketTypeDTO toDTO(TicketType ticketType);

  @Named("eventIdToEvent")
  protected Event eventIdToEvent(Long eventId)
  {
    if (eventId == null)
    {
      return null;
    }
    Optional<Event> event = eventRepository.findById(eventId);
    return event.orElseThrow(
        () -> new NoSuchElementException("Event with id " + eventId + " not found"));
  }

  @Named("eventToEventId")
  protected Long eventToEventId(Event event)
  {
    if (event == null)
    {
      return null;
    }
    return event.getId();
  }
}
