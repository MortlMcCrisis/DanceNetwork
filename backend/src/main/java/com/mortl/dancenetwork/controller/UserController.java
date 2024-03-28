package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.service.IUserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;

  @GetMapping("/{uuuid}")
  public UserDTO getUser(@PathVariable UUID uuuid){
    return userService.getUser(uuuid);
  }

  @PutMapping
  public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {

    return ResponseEntity.ok(userService.updateUser(userDTO));
  }
}
