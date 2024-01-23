package com.mortl.dancenetwork.user;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class UserUtil {

  private final JwtDecoder decoder;

  private final Keycloak keycloak;

  public User getCurrentUser() {
    Jwt jwt = getJwt();
    return new User(
        UUID.fromString(jwt.getClaim("sub")),
        jwt.getClaim("name"),
        "MALE" // TODO get from jwt token
    );
  }

  public List<User> fetchUsers(List<UUID> uuids) {
    return uuids.stream()
        .map(this::fetchUser)
        .toList();
  }

  public User fetchUser(UUID uuid) {
    UserRepresentation userRepresentation = keycloak.realm("dance-network")
        .users()
        .get(uuid.toString())
        .toRepresentation();
    return new User(
        UUID.fromString(userRepresentation.getId()),
        userRepresentation.getFirstName() + " " + userRepresentation.getLastName(),
        userRepresentation.getAttributes().get("sex").get(0));
  }

  public void addUserAttribute(String key, String value) {
    UserResource userResource = keycloak.realm("dance-network")
        .users()
        .get(getCurrentUser().uuid().toString());
    UserRepresentation userRepresentation = userResource
        .toRepresentation();
    Map<String, List<String>> attributes = Optional.ofNullable(
        userRepresentation.getAttributes())
        .orElse(new HashMap<>());
    attributes.put(key, List.of(value));
    userRepresentation.setAttributes(attributes);
    userResource.update(userRepresentation);
  }

  private Jwt getJwt(){
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    var authorizationHeader = request.getHeader("Authorization");
    return decoder.decode(authorizationHeader.split(" ")[1]);
  }
}
