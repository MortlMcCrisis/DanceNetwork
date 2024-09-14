package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.TicketTypeDTO;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.repository.EventRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TicketTypeMapper {

  @Autowired
  protected EventRepository eventRepository;

  @Mapping(target="event", expression="java(eventRepository.findById(dto.eventId()).get())")
  public abstract TicketType toModel(TicketTypeDTO dto);
  @Mapping(target="eventId", expression="java(ticketType.getEvent().getId())")
  public abstract TicketTypeDTO toDTO(TicketType ticketType);
}
