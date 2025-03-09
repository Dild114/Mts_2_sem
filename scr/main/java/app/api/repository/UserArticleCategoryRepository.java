package app.api.repository;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserArticleCategoryRepository extends JpaRepository<UserArticleCategory, Long> {

  Optional<UserArticleCategory> findByUserIdAndArticleId(Long userId, Long articleId);


  // Найти все категории для конкретного пользователя
  List<Category> findCategoriesByUserId(Long userId);

  // найти статьи для пользователя с категориями
  List<Article> findArticlesByUserId(Long userId);

  Optional<UserArticleCategory> findByUserIdAndArticleIdAndCategoryId(Long userId, Long articleId, Long categoryId);

  void deleteByUserIdAndArticleIdAndCategoryId(Long userId, Long articleId, Long categoryId);
}
