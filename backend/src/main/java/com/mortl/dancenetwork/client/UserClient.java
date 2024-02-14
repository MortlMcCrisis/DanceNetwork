package com.mortl.dancenetwork.client;

import com.mortl.dancenetwork.dto.Sex;
import com.mortl.dancenetwork.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class UserClient {

  private final JwtDecoder decoder;

  private final Keycloak keycloak;

  //TODO methode raus ziehen. Ist keine client methode
  public User getCurrentUser() {
    Jwt jwt = getJwt();
    return User.builder()
        .uuid(UUID.fromString(jwt.getClaim("sub")))
        .photoPath(jwt.getClaim("photoPath")) // TODO den namen des attributes gleich ziehen
        .username(jwt.getClaim("custom_username"))
        .firstName(jwt.getClaim("given_name"))
        .lastName(jwt.getClaim("family_name"))
        .sex(Sex.getIfNotNull(jwt.getClaim("sex")))
        .phone(jwt.getClaim("phone"))
        .build();
  }

  public List<User> fetchUsers(List<UUID> uuids) {
    return uuids.stream()
        .map(this::fetchUser)
        .toList();
  }

  public List<User> fetchUsers(){
    return getKeycloakUsers()
        .list()
        .stream()
        .map(this::keycloakRepresentationToUser)
        .toList();
  }

  public User fetchUser(UUID uuid) {
    UserRepresentation userRepresentation = getKeycloakUsers()
        .get(uuid.toString())
        .toRepresentation();
    return keycloakRepresentationToUser(userRepresentation);
  }

  private UsersResource getKeycloakUsers(){
    return keycloak.realm("dance-network")
        .users();
  }

  private User keycloakRepresentationToUser(UserRepresentation userRepresentation){
    Map<String, List<String>> attributes = Optional.ofNullable(
            userRepresentation.getAttributes())
        .orElse(new HashMap<>());
    return User.builder()
        .uuid(UUID.fromString(userRepresentation.getId()))
        .firstName(userRepresentation.getFirstName())
        .lastName(userRepresentation.getLastName())
        .username(getAttribute(attributes, "custom_username"))
        .sex(Sex.getIfNotNull(getAttribute(attributes, "sex")))
        .phone(getAttribute(attributes, "phone"))
        .photoPath(getAttribute(attributes, "photoPath"))
        .build();
  }

  private String getAttribute(Map<String, List<String>> attributes, String attributeName){
    List<String> values = attributes.getOrDefault(attributeName, List.of());
    if(values.size() > 0){
      return values.get(0);
    }
    return null;
  }

  public void setUsername(String username){
    //setValue(userRepresentation -> userRepresentation.setUsername(username));
    //TODO set custom username as instead of username
  }

  public void setFirstName(String firstName){
    setValue(userRepresentation -> userRepresentation.setFirstName(firstName));
  }

  public void setLastName(String lastName){
    setValue(userRepresentation -> userRepresentation.setLastName(lastName));
  }

  public void addUserAttribute(String key, String value) {
    Map<String, String> values = new HashMap<>();
    values.put(key, value);
    addUserAttributes(values);
  }

  public void addUserAttributes(Map<String, String> values) {
    setValue(userRepresentation -> {
      Map<String, List<String>> attributes = Optional.ofNullable(
              userRepresentation.getAttributes())
          .orElse(new HashMap<>());
      for (Entry<String, String> entry : values.entrySet()) {
        attributes.put(entry.getKey(), List.of(entry.getValue()));
      }
      userRepresentation.setAttributes(attributes);
      }
    );
  }

  public void updateUser(User user){
    UserResource userResource = getUserResource();
    UserRepresentation userRepresentation = userResource
        .toRepresentation();

    //userRepresentation.setUsername(user.username());
    userRepresentation.setFirstName(user.firstName());
    userRepresentation.setLastName(user.lastName());

    Map<String, List<String>> attributes = Optional.ofNullable(
            userRepresentation.getAttributes())
        .orElse(new HashMap<>());
    attributes.put("custom_username", List.of(user.username())); // TODO namen gleichziehen
    attributes.put("photoPath", List.of(user.photoPath()));
    attributes.put("sex", List.of(user.sex().name()));
    attributes.put("phone", List.of(user.phone()));
    userRepresentation.setAttributes(attributes);

    userResource.update(userRepresentation);
  }

  private void setValue(Consumer<UserRepresentation> function){
    UserResource userResource = getUserResource();
    UserRepresentation userRepresentation = userResource
        .toRepresentation();

    function.accept(userRepresentation);

    userResource.update(userRepresentation);
  }

  private UserResource getUserResource(){
    return keycloak.realm("dance-network")
        .users()
        .get(getCurrentUser().uuid().toString());
  }

  private Jwt getJwt(){
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    var authorizationHeader = request.getHeader("Authorization");
    return decoder.decode(authorizationHeader.split(" ")[1]);
  }
}
