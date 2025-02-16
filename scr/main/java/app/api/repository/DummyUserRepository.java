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
  public UserId createAccount(User user) {
    return new UserId(-1);
  }

  @Override
  public void deleteAccount(UserId userId) {
  }


  @Override
  public void updateAccount(UserId userId, User user) {
  }

  @Override
  public void updateNameAccount(UserId userId, String newName) {
  }
}
