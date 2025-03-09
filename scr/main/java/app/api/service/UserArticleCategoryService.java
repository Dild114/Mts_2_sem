package app.api.service;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.User;
import app.api.entity.UserArticleCategory;
import app.api.entity.UserId;
import app.api.repository.ArticleRepository;
import app.api.repository.UserArticleCategoryRepository;
import app.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class UserArticleCategoryService {

  private final UserArticleCategoryRepository userArticleCategoryRepository;
  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;

  public UserArticleCategoryService(UserArticleCategoryRepository userArticleCategoryRepository, UserRepository userRepository, ArticleRepository articleRepository) {
    this.userArticleCategoryRepository = userArticleCategoryRepository;
    this.userRepository = userRepository;
    this.articleRepository = articleRepository;
  }

  @Transactional
  public String getCategoriesByArticleIdAndUserId(ArticleId articleId, UserId userId) {
    Optional<UserArticleCategory> userArticleCategoryOptional = userArticleCategoryRepository.findByUserIdAndArticleId(userId.id(), articleId.id());
    if (userArticleCategoryOptional.isPresent()) {
      UserArticleCategory userArticleCategory = userArticleCategoryOptional.get();
      return userArticleCategory.getCategory().getName();
    } else {
      // видимо тут надо использовать ml и находить категорию
    }
    return null;
  }

  @Transactional
  public void setCategoriesByArticleIdAndUserId(ArticleId articleId, Category newCategory, UserId userId) {
    Optional<UserArticleCategory> userArticleCategoryOptional = userArticleCategoryRepository.findByUserIdAndArticleId(userId.id(), articleId.id());

    if (userArticleCategoryOptional.isPresent()) {
      UserArticleCategory userArticleCategory = userArticleCategoryOptional.get();
      userArticleCategory.setCategory(newCategory);
      userArticleCategoryRepository.save(userArticleCategory);
    } else {
      throw new RuntimeException("Not found user article category");
    }
  }
}
