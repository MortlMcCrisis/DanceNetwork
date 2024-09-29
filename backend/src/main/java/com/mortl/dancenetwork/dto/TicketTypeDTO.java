package com.mortl.dancenetwork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketTypeDTO(
    Long id,
    @NotBlank
    String name,
    @NotBlank
    String description,
    @NotNull
    Float price,
    @NotNull
    Integer contingent,
    @NotNull
    Long eventId)
{

}
