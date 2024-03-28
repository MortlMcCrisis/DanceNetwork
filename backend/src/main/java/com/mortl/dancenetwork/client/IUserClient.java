package com.mortl.dancenetwork.client;

import com.mortl.dancenetwork.entity.User;
import java.util.List;
import java.util.UUID;

public interface IUserClient {

  List<User> fetchUsers();

  User fetchUser(UUID uuid);

  void updateUser(User updatedUser);
}
