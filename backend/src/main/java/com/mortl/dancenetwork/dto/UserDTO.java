package com.mortl.dancenetwork.dto;

import com.mortl.dancenetwork.entity.User;
import java.util.UUID;

public record UserDTO(String photoPath, String username, String firstName, String lastName, Sex sex, String phone) {

  public User toEntity(UUID uuid){
    return User.builder()
        .uuid(uuid)
        .photoPath(photoPath())
        .username(username())
        .firstName(firstName())
        .lastName(lastName())
        .sex(sex())
        .phone(phone())
        .build();
  }
}
