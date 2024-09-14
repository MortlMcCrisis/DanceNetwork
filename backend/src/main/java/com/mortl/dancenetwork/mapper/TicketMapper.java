package com.mortl.dancenetwork.mapper;

import com.mortl.dancenetwork.dto.TicketDTO;
import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.repository.TicketTypeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TicketMapper {

  @Autowired
  protected TicketTypeRepository ticketTypeRepository;

  @Mapping(target="ticketTypeId", expression="java(ticket.getTicketType().getId())")
  public abstract TicketDTO toDTO(Ticket ticket);

  @Mapping(target="ticketType", expression="java(ticketTypeRepository.findById(ticketDTO.ticketTypeId()).get())")
  public abstract Ticket toModel(TicketDTO ticketDTO);
}