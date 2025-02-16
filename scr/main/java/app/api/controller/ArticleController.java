package app.api.controller;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.service.ArticleService;
import app.api.controller.interfaceDrivenControllers.ArticleControllerInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Tag(name = "Article API", description = "Управление статьями")
@RestController
public class ArticleController implements ArticleControllerInterface {

  private final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @Override
  public ResponseEntity<Map<Article, Category>> getArticles(int id) {
    log.info("Fetching articles for userId={}", id);
    try {
      Map<Article, Category> articles = articleService.getArticles(new UserId(id));
      return ResponseEntity.ok(articles);
    } catch (Exception e) {
      log.error("get articles failed", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
