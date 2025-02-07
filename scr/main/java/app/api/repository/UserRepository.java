package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

public interface UserRepository {
  UserId generateId();
  void createAccount(User user);
  void deleteAccount(UserId userId);
}
