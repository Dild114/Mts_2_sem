package app.api.service;

import app.api.entity.User;
import app.api.entity.UserId;
import app.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
  public UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserId createUser(String userName, String password) {
    log.info("Creating user {}", userName);
    User user = new User(userName, password);
    return userRepository.createAccount(user);
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