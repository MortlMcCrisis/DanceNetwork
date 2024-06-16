package com.mortl.dancenetwork.client;

import com.mortl.dancenetwork.entity.User;
import java.util.List;
import java.util.UUID;

public interface IUserClient {

  List<User> fetchUsers();

  List<User> fetchUsers(List<UUID> userUUIDs);

  void updateUser(User updatedUser, UUID uuid);
}
