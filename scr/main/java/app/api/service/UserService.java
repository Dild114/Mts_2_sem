package app.api.service;

import app.api.entity.UserId;
import app.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    return new UserId(-1);
  }

  public void deleteUser(UserId userId) {
    log.info("Deleting user {}", userId);
  }
}