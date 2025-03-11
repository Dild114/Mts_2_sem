package app.api.service;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserArticleCategory;
import app.api.entity.UserId;
import app.api.repository.ArticleRepository;
import app.api.repository.SiteRepository;
import app.api.repository.UserArticleCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ArticlesService {

  private final UserArticleCategoryRepository userArticleCategoryRepository;

  public ArticlesService(ArticleRepository articleRepository, UserArticleCategoryRepository userArticleCategoryRepository) {
    this.userArticleCategoryRepository = userArticleCategoryRepository;
  }

  @Transactional
  public List<Article> getArticles(UserId userId) {
    log.info("getArticles userId={}", userId.id());
    try {
      return userArticleCategoryRepository.findArticlesByUserId(userId.id());
    } catch (Exception e) {
      throw new RuntimeException("getArticles error", e);
    }
  }
}
