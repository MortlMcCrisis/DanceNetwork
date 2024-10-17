package com.mortl.dancenetwork.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javax.money.MonetaryAmount;

public record TicketTypeDTO(
    Long id,
    @NotBlank
    String name,
    @NotBlank
    String description,
    @NotNull
    @JsonSerialize(using = MonetaryAmountSerializer.class)
    @JsonDeserialize(using = MonetaryAmountDeserializer.class)
    MonetaryAmount price,
    @NotNull
    Integer contingent,
    @NotNull
    Long eventId)
{

}
