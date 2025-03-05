package app.api.controller;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.service.ArticlesService;
import app.api.controller.interfacedrivencontrollers.ArticleControllerInterface;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.internal.AtomicRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ArticlesController implements ArticleControllerInterface {
  private final RateLimiter rateLimiter = RateLimiter.ofDefaults("apiRateLimiter");
  private final ArticlesService articlesService;

  public ArticlesController(ArticlesService articlesService) {
    this.articlesService = articlesService;
  }


  @Override
  public ResponseEntity<List<Article>> getArticles(int id) {
    log.info("Fetching articles for userId={}", id);
    try {
      List<Article> articles = articlesService.getArticles(new UserId(id));
      return rateLimiter.executeSupplier(() -> ResponseEntity.ok().body(articles));
    } catch (Exception e) {
      log.error("get articles failed", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
