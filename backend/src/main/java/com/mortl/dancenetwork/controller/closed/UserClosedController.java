package com.mortl.dancenetwork.controller.closed;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/closed/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserClosedController {

  private final IUserService userService;

  @PutMapping
  public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
    return ResponseEntity.ok(userService.updateUser(userDTO));
  }
}