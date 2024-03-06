package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.model.Event;
import com.mortl.dancenetwork.model.TicketType;

public record TicketTypeDTO(Long id, String name, String description, Float price, Long eventId){

  public TicketType toModel(Event event){
    return TicketType.builder()
        .id(this.id)
        .name(this.name)
        .description(this.description)
        .price(this.price)
        .event(event)
        .build();
  }

  public static TicketTypeDTO fromModel(TicketType ticketType){
    return new TicketTypeDTO(
        ticketType.getId(),
        ticketType.getName(),
        ticketType.getDescription(),
        ticketType.getPrice(),
        ticketType.getEvent().getId());
  }
}
