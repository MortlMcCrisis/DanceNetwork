package com.mortl.dancenetwork.dto;

import java.util.UUID;

public record UserDTO(

    UUID uuid,
    String photoPath,
    String username,
    String firstName,
    String lastName,
    Gender gender,
    String phone) {
}
