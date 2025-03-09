package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  Optional<Article> findById(Long articleId);
  List<Article> findArticlesBySiteId(Long siteId);
  void deleteById(Long articleId);
  void deleteAllBySiteId(Long siteId);
}
