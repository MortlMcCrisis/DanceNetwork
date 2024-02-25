package com.mortl.dancenetwork.client;

import com.mortl.dancenetwork.dto.Sex;
import com.mortl.dancenetwork.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

public interface IUserClient {

  List<User> fetchUsers();

  User fetchUser(UUID uuid);

  void updateUser(User updatedUser);
}
