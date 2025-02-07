package app.api.controller;

import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.UserId;
import app.api.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class ArticleController {
  public final ArticleService articleService;

  @Autowired
  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }


  @GetMapping("/articles")
  public String getArticles(
      @RequestParam int userId, Model model) {
    try {
      Map<Article, Category> articles = articleService.getArticles(new UserId(userId));
      model.addAttribute("articles", articles);
      return "articles";
    } catch (Exception e) {
      model.addAttribute("error", "get articles error");
      return "error";
    }
  }
}
