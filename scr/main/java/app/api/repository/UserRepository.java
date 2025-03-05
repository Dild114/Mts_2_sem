package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserId;

public interface UserRepository {
  UserId generateId();
  UserId createUser(User user);
  void deleteUser(UserId userId);
  void updateUser(UserId userId, User user);
  void updateNameUser(UserId userID, String newName);

}
