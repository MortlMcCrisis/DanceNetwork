package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

  UserDTO updateUser(UserDTO userDTO);

  List<User> getAllUsers();

  Optional<User> getCurrentUser();

  User getNonNullCurrentUser();

  List<UserDTO> getUsers(List<UUID> userUUIDs);
}
