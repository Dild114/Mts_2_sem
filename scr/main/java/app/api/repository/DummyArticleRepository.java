package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DummyArticleRepository implements ArticleRepository {
  int countId = 0;
  @Override
  public ArticleId generateId() {
    countId += 1;
    return new ArticleId(countId);
  }

  @Override
  public Map<Article, Category> getArticles(UserId userId) {
    return Map.of();
  }
}
