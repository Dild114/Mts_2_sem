package app.api.service;

import app.api.entity.User;
import app.api.entity.UserId;
import app.api.exception.UserAlreadyExistsException;
import app.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersService {
  public UserRepository userRepository;

  public UsersService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  // Обеспечение создания пользователя
  @Retryable(retryFor = IllegalArgumentException.class, maxAttempts = 5, backoff = @Backoff(delay = 10000))
  public UserId createUser(String userName, String password) {
    log.info("Creating user {}", userName);
    User user = new User(userName, password);
    try {
      return userRepository.createAccount(user);
    } catch (Exception e) {
      throw new UserAlreadyExistsException("wrong data");
    }
  }

  public void deleteUser(UserId userId) {
    userRepository.deleteAccount(userId);
    log.info("Deleting user {}", userId);
  }

  public void updateUserData(UserId userId, User user) {
    log.info("update user Data in UserService");
    userRepository.updateAccount(userId, user);
  }

  public void updateUserName(UserId userId, String newName) {
    log.info("update Username with id: {}", userId.id());
    userRepository.updateNameAccount(userId, newName);
  }
}