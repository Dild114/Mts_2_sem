package app.api.controller;

import app.api.entity.UserId;
import app.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/signup")
@RestController
public class UserController {
  public final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<?> createUser(
      @RequestBody String name,
      @RequestBody String password) {
    log.info("createUser");
    if (name.isEmpty() || password.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    try {
      UserId userId = userService.createUser(name, password);
      return ResponseEntity.ok(userId);
    } catch (Exception e) {
      log.error("createUser failed", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable int id) {
    log.info("deleteUser");
    UserId userId = new UserId(id);
    try {
      userService.deleteUser(new UserId(id));
      return ResponseEntity.ok(userId);
    } catch (Exception e) {
      log.error("deleteUser", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
