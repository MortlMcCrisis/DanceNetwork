package com.mortl.dancenetwork.dto;

public record TicketDTO(Long ticketId, String ticketDescription, /* TODO qrcode...*/Long ticketTypeId, PersonalTicketDataDTO ticketData, EventDTO eventData) {
//TODO create mapper
}
