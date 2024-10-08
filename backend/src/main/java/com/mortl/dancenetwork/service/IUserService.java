package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService
{

  User updateUser(User user);

  List<User> getAllUsers();

  Optional<User> getCurrentUser();

  User getNonNullCurrentUser();

  List<User> getUsers(List<UUID> userUUIDs);
}
