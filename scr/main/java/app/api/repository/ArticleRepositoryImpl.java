package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
  int countId = 0;

  @Autowired
  private RestTemplate restTemplate;


  @Override
  public ArticleId generateId() {
    countId += 1;
    return new ArticleId(countId);
  }

  @Override
  public Map<Article, Category> getArticles(UserId userId) {
    return Map.of();
  }

  @Override
  public String getRandomUrlRest() {
    String url = "https://ru.wikipedia.org/wiki/";
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url + (int) (Math.random() * 100), null, String.class);

    return responseEntity.getBody();
  }

  @Override
  public String getRandomUrlRestWebClient() {
    WebClient webClient = WebClient.builder()
        .baseUrl("https://ru.wikipedia.org/wiki/" + (int) (Math.random() * 100))
        .build();
    return webClient.get().retrieve().bodyToMono(String.class).block();
  }
}
