package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.Gender;
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
