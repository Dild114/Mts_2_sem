package app.api.service;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class ArticleService {
  public ArticleRepository articleRepository;


  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public Map<Article, Category> getArticles(UserId userId) {
    log.info("getArticles userId={}", userId);
    return null;
  }

}
