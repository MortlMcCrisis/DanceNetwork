package com.mortl.dancenetwork.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PaymentRequestDTO(
    @NotEmpty
    List<TicketDTO> tickets)
{

}