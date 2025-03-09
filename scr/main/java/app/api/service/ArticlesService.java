package app.api.service;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.repository.ArticleRepository;
import app.api.repository.SiteRepository;
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

  private final ArticleRepository articleRepository;
  private final SiteRepository siteRepository;

  public ArticlesService(ArticleRepository articleRepository, SiteRepository siteRepository) {
    this.articleRepository = articleRepository;
    this.siteRepository = siteRepository;
  }

  @Transactional
  public List<Article> getArticles(SiteId siteId, UserId userId) {
    log.info("getArticles userId={}", userId);
    Optional<Site> siteOptional = siteRepository.findById(siteId.siteId());
    if (siteOptional.isPresent()) {
      Site site = siteOptional.get();
      if (site.getUser().getId().equals(userId)) {
        List<Article> articles =  articleRepository.findArticlesBySiteId(siteId.siteId());
        return articles;
      } else {
        throw new RuntimeException("Not found");
      }
    }
  }

}
