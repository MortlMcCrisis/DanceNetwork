package com.mortl.dancenetwork.client.Impl;

import com.mortl.dancenetwork.client.IUserClient;
import com.mortl.dancenetwork.enums.Gender;
import com.mortl.dancenetwork.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
public class UserClientImpl implements IUserClient {

  private final Keycloak keycloak;

  public UserClientImpl(Keycloak keycloak) {
    this.keycloak = keycloak;
  }

  @Override
  public List<User> fetchUsers(List<UUID> uuids) {
    return uuids.stream()
        .map(this::fetchUser)
        .toList();
  }

  private User fetchUser(UUID uuid) {
    UserRepresentation userRepresentation = getKeycloakUsers()
        .get(uuid.toString())
        .toRepresentation();
    return keycloakRepresentationToUser(userRepresentation);
  }

  @Override
  public List<User> fetchUsers(){
    return getKeycloakUsers()
        .list()
        .stream()
        .map(this::keycloakRepresentationToUser)
        .toList();
  }

  private UsersResource getKeycloakUsers(){
    return keycloak.realm("dance-network")
        .users();
  }

  private User keycloakRepresentationToUser(UserRepresentation userRepresentation){
    Map<String, List<String>> attributes = Optional.ofNullable(
            userRepresentation.getAttributes())
        .orElse(new HashMap<>());
    return new User(
        UUID.fromString(userRepresentation.getId()),
        getAttribute(attributes, "email"),
        getAttribute(attributes, "custom_username"),
        userRepresentation.getFirstName(),
        userRepresentation.getLastName(),
        Gender.getIfNotNull(getAttribute(attributes, "gender")),
        getAttribute(attributes, "phone"),
        getAttribute(attributes, "photo_path"));
  }

  private String getAttribute(Map<String, List<String>> attributes, String attributeName){
    List<String> values = attributes.getOrDefault(attributeName, List.of());
    if(values.size() > 0){
      return values.get(0);
    }
    return null;
  }

//  public void setUsername(String username){
//    //setValue(userRepresentation -> userRepresentation.setUsername(username));
//    //TODO set custom username as instead of username
//  }
//
//  public void setFirstName(UUID userUUID,String firstName){
//    setValue(userUUID, userRepresentation -> userRepresentation.setFirstName(firstName));
//  }
//
//  public void setLastName(UUID userUUID,String lastName){
//    setValue(userUUID, userRepresentation -> userRepresentation.setLastName(lastName));
//  }
//
//  public void addUserAttribute(UUID userUUID, String key, String value) {
//    Map<String, String> values = new HashMap<>();
//    values.put(key, value);
//    addUserAttributes(userUUID, values);
//  }
//
//  public void addUserAttributes(UUID userUUID, Map<String, String> values) {
//    setValue(userUUID, userRepresentation -> {
//      Map<String, List<String>> attributes = Optional.ofNullable(
//              userRepresentation.getAttributes())
//          .orElse(new HashMap<>());
//      for (Entry<String, String> entry : values.entrySet()) {
//        attributes.put(entry.getKey(), List.of(entry.getValue()));
//      }
//      userRepresentation.setAttributes(attributes);
//      }
//    );
//  }

  @Override
  public void updateUser(User updatedUser, UUID uuid){
    UserResource currentUserResource = getUserResource(uuid);
    UserRepresentation userRepresentation = currentUserResource
        .toRepresentation();

    userRepresentation.setFirstName(updatedUser.getFirstName());
    userRepresentation.setLastName(updatedUser.getLastName());

    Map<String, List<String>> attributes = Optional.ofNullable(
            userRepresentation.getAttributes())
        .orElse(new HashMap<>());
    attributes.put("custom_username", List.of(updatedUser.getUsername()));
    attributes.put("photo_path", List.of(updatedUser.getPhotoPath()));
    attributes.put("gender", List.of(updatedUser.getGender().name()));
    attributes.put("phone", List.of(updatedUser.getPhone()));
    userRepresentation.setAttributes(attributes);

    currentUserResource.update(userRepresentation);
  }

//  private void setValue(UUID userUUID, Consumer<UserRepresentation> function){
//    UserResource userResource = getUserResource(userUUID);
//    UserRepresentation userRepresentation = userResource
//        .toRepresentation();
//
//    function.accept(userRepresentation);
//
//    userResource.update(userRepresentation);
//  }

  private UserResource getUserResource(UUID userUUID){
    return keycloak.realm("dance-network")
        .users()
        .get(userUUID.toString());
  }
}
