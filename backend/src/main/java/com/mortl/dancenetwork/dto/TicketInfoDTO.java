package com.mortl.dancenetwork.dto;

import jakarta.validation.constraints.NotEmpty;

public record TicketInfoDTO(
    @NotEmpty
    TicketDTO ticket,
    @NotEmpty
    TicketTypeDTO ticketType,
    @NotEmpty
    EventDTO event)
{

}
