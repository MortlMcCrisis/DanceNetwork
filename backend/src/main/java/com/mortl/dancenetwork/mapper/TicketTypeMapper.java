package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.entity.Event;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.repository.EventRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;
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
  @Mapping(target = "price", source = "price", qualifiedByName = "floatToMonetaryAmount")
  public abstract TicketType toEntity(TicketTypeDTO dto);

  @Mapping(target = "eventId", source = "event", qualifiedByName = "eventToEventId")
  @Mapping(target = "price", source = "price", qualifiedByName = "monetaryAmountToFloat")
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

  @Named("floatToMonetaryAmount")
  protected MonetaryAmount floatToMonetaryAmount(Float price)
  {
    if (price == null)
    {
      return null;
    }
    // Verwende eine Standardwährung, z.B. EUR
    return Money.of(price, "EUR");
  }

  // Helper-Methode zur Konvertierung von MonetaryAmount zu Float
  @Named("monetaryAmountToFloat")
  protected Float monetaryAmountToFloat(MonetaryAmount monetaryAmount)
  {
    if (monetaryAmount == null)
    {
      return null;
    }
    return monetaryAmount.getNumber().floatValue();
  }
}
