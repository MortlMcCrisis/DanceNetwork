package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.client.IUserClient;
import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.enums.Gender;
import com.mortl.dancenetwork.mapper.UserMapper;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserServiceImpl implements IUserService {

  private final JwtDecoder decoder;

  private final IUserClient userClient;

  private final UserMapper userMapper;

  public UserServiceImpl(JwtDecoder decoder, IUserClient userClient, UserMapper userMapper) {
    this.decoder = decoder;
    this.userClient = userClient;
    this.userMapper = userMapper;
  }


  @Override
  public UserDTO updateUser(UserDTO userDTO) {
    //TODO find better solution to set uuid
    userClient.updateUser(userMapper.toEntity(userDTO), getCurrentUser().get().getUuid());
    return userDTO;
  }

  @Override
  public List<User> getAllUsers(){
    return userClient.fetchUsers();
  }

  @Override
  public Optional<User> getCurrentUser() {
    Optional<Jwt> jwtOptional = getJwt();
    if(jwtOptional.isEmpty()){
      return Optional.empty();
    }
    Jwt jwt = jwtOptional.get();
    return Optional.of(new User(
        UUID.fromString(jwt.getClaim("sub")),
        jwt.getClaim("email"),
        jwt.getClaim("custom_username"),
        jwt.getClaim("given_name"),
        jwt.getClaim("family_name"),
        Gender.getIfNotNull(jwt.getClaim("gender")),
        jwt.getClaim("phone"),
        jwt.getClaim("photo_path")));
  }

  @Override
  public User getNonNullCurrentUser() {
    Optional<User> currentUser = getCurrentUser();
    if(currentUser.isEmpty()){
      throw new IllegalStateException("Current user must not be null.");
    }
    return currentUser.get();
  }

  @Override
  public List<UserDTO> getUsers(List<UUID> userUUIDs) {
    return userClient.fetchUsers(userUUIDs).stream()
        .map(user -> userMapper.toDTO(user))
        .toList();
  }

  private Optional<Jwt> getJwt() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    try {
      String authorizationHeader = request.getHeader("Authorization");
      return Optional.of(decoder.decode(authorizationHeader.split(" ")[1]));
    }
    catch(NullPointerException e){
      return Optional.empty();
    }
  }
}
