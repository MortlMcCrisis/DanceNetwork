package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.mapper.UserMapper;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.service.IUserService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/users")
public class UserOpenController
{

  private final IUserService userService;

  private final UserMapper userMapper;

  public UserOpenController(IUserService userService, UserMapper userMapper)
  {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getUsers(@RequestParam List<UUID> userUUID)
  {
    List<User> users = userService.getUsers(userUUID);
    return ResponseEntity.ok(users.stream().map(userMapper::toDTO).toList());
  }
}
