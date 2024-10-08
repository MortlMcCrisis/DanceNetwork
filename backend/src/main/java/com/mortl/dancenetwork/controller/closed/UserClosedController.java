package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.mapper.UserMapper;
import com.mortl.dancenetwork.model.User;
import com.mortl.dancenetwork.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/users")
public class UserClosedController
{

  private final IUserService userService;

  private final UserMapper userMapper;

  public UserClosedController(IUserService userService, UserMapper userMapper)
  {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PutMapping
  public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO)
  {

    User user = userService.updateUser(userMapper.toModel(userDTO));
    return ResponseEntity.ok(userMapper.toDTO(user));
  }
}
