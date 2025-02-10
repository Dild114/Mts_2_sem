package app.api.controller;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Slf4j
@Controller
public class ArticleController {
  public final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }


  @GetMapping("/articles")
  public ResponseEntity<Map<Article, Category>> getArticles(
      @RequestBody int id) {
    log.info("getArticles userId={}", id);
    try {
      Map<Article, Category> articles = articleService.getArticles(new UserId(id));
      return ResponseEntity.ok(articles);
    } catch (Exception e) {
      log.error("error get Articles", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
