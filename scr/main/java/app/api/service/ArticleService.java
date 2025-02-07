package app.api.service;

import app.api.controller.ArticleController;
import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArticleService {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);
  public ArticleRepository articleRepository;
  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public Map<Article, Category> getArticles(UserId userId) {
    LOG.info("getArticles userId={}", userId);
    return null;
  }

}
