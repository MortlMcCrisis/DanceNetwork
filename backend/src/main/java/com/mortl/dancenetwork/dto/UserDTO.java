package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.entity.User;
import java.util.UUID;

public record UserDTO(
    String photoPath,
    String username,
    String firstName,
    String lastName,
    Gender gender,
    String phone) {

  public User toEntity(UUID uuid){
    return User.builder()
        .uuid(uuid)
        .photoPath(photoPath())
        .username(username())
        .firstName(firstName())
        .lastName(lastName())
        .gender(gender())
        .phone(phone())
        .build();
  }

  public static UserDTO fromEntity(User user){
    return new UserDTO(user.photoPath(),
        user.username(),
        user.firstName(),
        user.lastName(),
        user.gender(),
        user.phone());
  }
}
