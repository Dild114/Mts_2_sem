package app.api.service;

import app.api.entity.UserId;
import app.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
  public UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserId createUser(String userName, String password) {
    LOG.info("Creating user {}", userName);
    return null;
  }

  public void deleteUser(UserId userId) {
    LOG.info("Deleting user {}", userId);
  }
}