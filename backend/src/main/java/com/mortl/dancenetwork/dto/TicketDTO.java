package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.DancingRole;
import com.mortl.dancenetwork.enums.Gender;
import java.time.LocalDateTime;
import java.util.UUID;

public record TicketDTO(
    Long id,
    UUID owner,
    Long ticketTypeId,
    String firstName,
    String lastName,
    String address,
    String country,
    String email,
    String phone,
    DancingRole dancingRole,
    Gender gender,
    LocalDateTime buyDate)
{

}
