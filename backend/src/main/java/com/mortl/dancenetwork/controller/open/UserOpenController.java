package com.mortl.dancenetwork.controller.open;

import com.mortl.dancenetwork.dto.UserDTO;
import com.mortl.dancenetwork.service.IUserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserOpenController {

  private final IUserService userService;

  @GetMapping
  public ResponseEntity<List<UserDTO>> getUsers(@RequestParam List<UUID> userUUID) {
    return ResponseEntity.ok(userService.getUsers(userUUID));
  }
}
