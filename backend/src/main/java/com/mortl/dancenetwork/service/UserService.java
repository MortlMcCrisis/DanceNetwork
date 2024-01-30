package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {

  private final UserClient userClient;

  public UserDTO updateUser(UserDTO userDTO) {

    userClient.updateUser(userDTO.toEntity(userClient.getCurrentUser().uuid()));

    return userDTO;
  }
}
