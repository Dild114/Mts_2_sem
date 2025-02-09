package app.api.controller;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.service.ArticleService;
import app.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class ArticleController {
  public final ArticleService articleService;
  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }


  @GetMapping("/articles")
  public String getArticles(
      @RequestParam int userId, Model model) {
    LOG.info("getArticles userId={}", userId);
    try {
      Map<Article, Category> articles = articleService.getArticles(new UserId(userId));
      model.addAttribute("articles", articles);
      return "articles";
    } catch (Exception e) {
      LOG.error("error get Articles", e);
      model.addAttribute("error", "get articles error");
      return "error";
    }
  }
}
