package com.mortl.dancenetwork.dto;

public record TicketInfoDTO (
  TicketDTO ticket,
  TicketTypeDTO ticketType,
  EventDTO event){
}
