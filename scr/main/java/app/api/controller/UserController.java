package app.api.controller;

import app.api.controller.interfaceDrivenControllers.UserControllerInterface;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController implements UserControllerInterface {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public ResponseEntity<UserId> createUser(@RequestBody UserRequest userRequest) {
    log.info("createUser");
    if (userRequest == null || userRequest.name() == null || userRequest.name().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    try {
      UserId userId = userService.createUser(userRequest.name(), userRequest.password());
      return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    } catch (Exception e) {
      log.error("createUser failed", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<?> deleteUser(int id) {
    log.info("deleteUser");
    UserId userId = new UserId(id);
    try {
      userService.deleteUser(userId);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      log.error("deleteUser failed", e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Override
  public ResponseEntity<?> updateUserData(int id, UserRequest userRequest) {
    log.info("Update user data with id: {}", id);
    UserId userId = new UserId(id);
    User user = new User(userRequest.name(), userRequest.password());
    try {
      userService.updateUserData(userId, user);
      return ResponseEntity.ok("update successful");
    } catch (Exception e) {
      log.error("update user failed ", e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Override
  public ResponseEntity<?> updateUserName(int id, String name) {
    log.info("update username with id: {}", id);
    UserId userId = new UserId(id);
    try {
      userService.updateUserName(userId, name);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      log.error("update UserName failed", e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}