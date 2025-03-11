package app.e2e;

import app.api.controller.UsersRequest;
import app.api.entity.Article;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class E2eTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;


  @Bean
  public RestTemplate customRestTemplate() {
    return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
  }



  @Test
  void e2e() {
    UsersRequest usersRequest = new UsersRequest("John", "qwert");
    UsersRequest usersRequest2 = new UsersRequest("John", "qwert");
    String url = "http://localhost:" + port;
    ResponseEntity<UserId> responseEntity = restTemplate.postForEntity(url + "/signup", usersRequest, UserId.class);
    ResponseEntity<UserId> responseEntity2 = restTemplate.postForEntity(url + "/signup", usersRequest2, UserId.class);

    // Create user
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(responseEntity.getBody().id(), 1);

    assertEquals(responseEntity2.getStatusCode(), HttpStatus.CREATED);
    assertEquals(responseEntity2.getBody().id(), 2);

    // Delete user
    ResponseEntity<Void> responseEntity3 = restTemplate.exchange(url + "/signup/2", HttpMethod.DELETE, null, Void.class);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity3.getStatusCode());

    // get all site

    ResponseEntity<HashMap<String, Integer>> responseEntity4 = restTemplate.getForEntity(url + "/site/all", null, HashMap.class);
    assertEquals(HttpStatus.OK, responseEntity4.getStatusCode());


    // add category
    ResponseEntity<CategoryId> responseEntity5 = restTemplate.postForEntity(url + "/category/" + 1, "qwerty", CategoryId.class);
    assertEquals(HttpStatus.OK, responseEntity5.getStatusCode());
    assertEquals(responseEntity5.getBody().id(), 1);

    // delete category
    Object requestBody = new UserId(1L);
    HttpEntity<Object> entity = new HttpEntity<>(requestBody);
    ResponseEntity<Void> responseEntity6 = restTemplate.exchange(url + "/category/1", HttpMethod.DELETE, entity, Void.class);
    assertEquals(HttpStatus.NO_CONTENT, responseEntity6.getStatusCode());
    // get articles
    Object requestBody2 = new UserId(1L);
    HttpEntity<Object> entity2 = new HttpEntity<>(requestBody);
    ResponseEntity<Map<Article, Category>> responseEntity7 = restTemplate.getForEntity(url + "/article", null, HashMap.class);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity7.getStatusCode());
  }
}
