package app.api.controller;

import app.api.controller.interfacedrivencontrollers.UserControllerInterface;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.service.UsersService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UsersController implements UserControllerInterface {
  private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("apiCircuitBreaker");
  private final UsersService usersService;

  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public ResponseEntity<UserId> createUser(@RequestBody UsersRequest usersRequest) {
    log.info("createUser");
    if (usersRequest == null || usersRequest.name() == null || usersRequest.name().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    try {
      UserId userId = usersService.createUser(usersRequest.name(), usersRequest.password());
      return circuitBreaker.executeSupplier(() -> ResponseEntity.status(HttpStatus.CREATED).body(userId));
    } catch (Exception e) {
      log.error("createUser failed", e);
      return circuitBreaker.executeSupplier(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
  }

  @Override
  public ResponseEntity<Void> deleteUser(Long id) {
    log.info("deleteUser");
    UserId userId = new UserId(id);
    try {
      usersService.deleteUser(userId);
      return circuitBreaker.executeSupplier(() -> ResponseEntity.noContent().build());
    } catch (Exception e) {
      log.error("deleteUser failed", e);
      return circuitBreaker.executeSupplier(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
  }

  @Override
  public ResponseEntity<?> updateUserData(Long id, UsersRequest usersRequest) {
    if (usersRequest == null || usersRequest.name() == null || usersRequest.name().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    log.info("Update user data with id: {}", id);
    UserId userId = new UserId(id);
    User user = new User(usersRequest.name(), usersRequest.password());
    try {
      usersService.updateUserData(userId, user);
      return circuitBreaker.executeSupplier(() -> ResponseEntity.ok("update successful"));
    } catch (Exception e) {
      log.error("update user failed ", e);
      return circuitBreaker.executeSupplier(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
  }

  @Override
  public ResponseEntity<Void> updateUserName(Long id, String name) {
    log.info("update username with id: {}", id);
    UserId userId = new UserId(id);
    try {
      usersService.updateUserName(userId, name);
      return circuitBreaker.executeSupplier(() -> ResponseEntity.noContent().build());
    } catch (Exception e) {
      log.error("update UserName failed", e);
      return circuitBreaker.executeSupplier(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
  }
}