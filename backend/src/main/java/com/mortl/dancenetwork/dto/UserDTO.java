package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;

public record UserDTO(

    @NotEmpty
    UUID uuid,
    String photoPath,
    String username,
    String firstName,
    String lastName,
    Gender gender,
    String phone)
{

}
