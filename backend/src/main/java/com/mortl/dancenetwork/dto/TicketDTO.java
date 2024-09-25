package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.Gender;
import com.mortl.dancenetwork.enums.Role;

public record TicketDTO(
    Long id,
    Long ticketTypeId,
    String firstName,
    String lastName,
    String address,
    String country,
    String email,
    String phone,
    Role role,// TODO change everywhere to dancingRole or something like that. "role" is very generic and will be difficult, when e.g. administration roles are introduced
    Gender gender) {
}
