package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.client.IUserClient;
import com.mortl.dancenetwork.dto.Sex;
import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.entity.User;
import com.mortl.dancenetwork.service.IUserService;
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
public class UserServiceImpl implements IUserService {

  private final JwtDecoder decoder;

  private final IUserClient userClient;

  @Override
  public UserDTO updateUser(UserDTO userDTO) {

    userClient.updateUser(userDTO.toEntity(getCurrentUser().uuid()));

    return userDTO;
  }

  @Override
  public List<User> getAllUsers(){
    return userClient.fetchUsers();
  }

  @Override
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

  @Override
  public UserDTO getUser(UUID userUUID) {
    return UserDTO.fromModel(userClient.fetchUser(userUUID));
  }

  private Jwt getJwt() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    var authorizationHeader = request.getHeader("Authorization");
    return decoder.decode(authorizationHeader.split(" ")[1]);
  }
}
