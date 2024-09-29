package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.DancingRole;
import com.mortl.dancenetwork.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record TicketDTO(
    Long id,
    @NotEmpty
    UUID owner,
    @NotNull
    Long ticketTypeId,
    @NotBlank
    String firstName,
    @NotBlank
    String lastName,
    @NotBlank
    String address,
    @NotBlank
    String country,
    @NotBlank
    String email,
    String phone,
    @NotEmpty
    DancingRole dancingRole,
    @NotEmpty
    Gender gender,
    LocalDateTime buyDate)
{

}
