package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.entity.User;
import java.util.UUID;

//TODO use mapper
public record UserDTO(

    UUID uuid,
    String photoPath,
    String username,
    String firstName,
    String lastName,
    Gender gender,
    String phone) {
}
