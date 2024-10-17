package com.mortl.dancenetwork.dto;

import java.util.List;

public record UpdateTicketTypeRequestDTO
    (
        long eventId,
        List<TicketTypeDTO> ticketTypeDTOs
    )
{

}
