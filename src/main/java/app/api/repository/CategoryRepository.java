package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findById(Long categoryId);
  List<Category> findAllByUserId(Long userId);
  void deleteAllByUserId(Long userId);
  void deleteById(Long categoryId);
}
