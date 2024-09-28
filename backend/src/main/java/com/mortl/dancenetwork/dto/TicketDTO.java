package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.Gender;
import com.mortl.dancenetwork.enums.Role;
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
    Role role,// TODO change everywhere to dancingRole or something like that. "role" is very generic and will be difficult, when e.g. administration roles are introduced
    Gender gender,
    LocalDateTime buyDate) {
}
