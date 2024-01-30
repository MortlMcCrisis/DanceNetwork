package com.mortl.dancenetwork.controller;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PutMapping
  public ResponseEntity updateNewsfeedEntry(@RequestBody UserDTO userDTO) {

    return ResponseEntity.ok(userService.updateUser(userDTO));
  }
}
