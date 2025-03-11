package app.api.repository;

import app.api.entity.User;
import app.api.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

  Optional<User> findById(Long userId);
  void deleteById(Long userId);
}
