package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

@Repository
public class DummyUserRepository implements UserRepository {
  @Override
  public UserId generateId() {
    return null;
  }

  @Override
  public void createAccount(User user) {
  }

  @Override
  public void deleteAccount(UserId userId) {
  }
}
