package app.api.service;

import app.api.entity.User;
import app.api.entity.UserId;
import app.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersService {
  private final UserRepository userRepository;

  public UsersService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Обеспечение создания пользователя
  @Retryable(retryFor = IllegalArgumentException.class, maxAttempts = 5, backoff = @Backoff(delay = 10000))
  @Transactional
  public UserId createUser(String name, String password) {
    User user = userRepository.save(new User(name, password));
    log.info("Creating user {}", name);
    return new UserId(user.getId());
  }

  @Transactional
  public void deleteUser(UserId userId) {
    userRepository.deleteById(userId);
    log.info("Deleting user {}", userId);
  }

  @Transactional
  public void updateUserData(UserId userId, User user) {
    User oldUser  = userRepository.findById(userId.id()).orElseThrow();
    oldUser.setName(user.getName());
    oldUser.setPassword(user.getPassword());
    userRepository.save(oldUser);
    log.info("update user Data in UserService");
  }

  @Transactional
  public void updateUserName(UserId userId, String newName) {
    User oldUser = userRepository.findById(userId.id()).orElseThrow();
    oldUser.setName(newName);
    userRepository.save(oldUser);
    log.info("update Username with id: {}", userId.id());
  }
}