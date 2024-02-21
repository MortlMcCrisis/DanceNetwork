package com.mortl.dancenetwork.service;

import com.mortl.dancenetwork.dto.Sex;
import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.client.UserClient;
import com.mortl.dancenetwork.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class UserService {

  private final JwtDecoder decoder;

  private final UserClient userClient;

  public UserDTO updateUser(UserDTO userDTO) {

    userClient.updateUser(userDTO.toEntity(getCurrentUser().uuid()));

    return userDTO;
  }

  public List<User> getAllUsers(){
    return userClient.fetchUsers();
  }

  public User getCurrentUser() {
    Jwt jwt = getJwt();
    return User.builder()
        .uuid(UUID.fromString(jwt.getClaim("sub")))
        .photoPath(jwt.getClaim("photo_path"))
        .username(jwt.getClaim("custom_username"))
        .firstName(jwt.getClaim("given_name"))
        .lastName(jwt.getClaim("family_name"))
        .sex(Sex.getIfNotNull(jwt.getClaim("sex")))
        .phone(jwt.getClaim("phone"))
        .build();
  }

  public User getUser(UUID userUUID) {
    return userClient.fetchUser(userUUID);
  }

  private Jwt getJwt(){
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    var authorizationHeader = request.getHeader("Authorization");
    return decoder.decode(authorizationHeader.split(" ")[1]);
  }
}
