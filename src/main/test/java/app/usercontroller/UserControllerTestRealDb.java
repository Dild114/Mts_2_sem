package app.usercontroller;

import app.api.controller.UsersRequest;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.testcontainers.lifecycle.Startables;

import java.util.Optional;


//@ContextConfiguration(initializers = DatabaseSuite.Initializer.class)
//class DatabaseSuite {
//  private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:14");
//
//  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//    @Override
//    public void initialize(ConfigurableApplicationContext context) {
//      Startables.deepStart(POSTGRES).join();
//
//      TestPropertyValues.of(
//          "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
//          "spring.datasource.username=" + POSTGRES.getUsername(),
//          "spring.datasource.password=" + POSTGRES.getPassword()
//      ).applyTo(context);
//    }
//  }
//}

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserControllerTestRealDb {

  @Autowired
  private UserRepository userRepository;


  @Test
  @Transactional
  public void CreateUser() {
    UsersRequest usersRequest = new UsersRequest("namename", "test");
    User newUser = new User(usersRequest.name(), usersRequest.password());
    User user = userRepository.save(newUser);

    Optional<User> userOptional = userRepository.findById(user.getId());
    assertTrue(userOptional.isPresent());
    User user1 = userOptional.get();

    assertEquals(user.getId(), user1.getId());
    assertEquals(user.getName(), user1.getName());
  }

  @Test
  @Transactional
  public void DeleteUser() {
    UsersRequest usersRequest = new UsersRequest("namename", "test");
    User newUser = new User(usersRequest.name(), usersRequest.password());
    User user = userRepository.save(newUser);

    Optional<User> userOptional = userRepository.findById(user.getId());

    assertTrue(userOptional.isPresent());
    User user1 = userOptional.get();

    assertEquals(user.getId(), user1.getId());
    assertEquals(user.getName(), user1.getName());


    userRepository.deleteById(user1.getId());
    Optional<User> userOptional1 = userRepository.findById(user1.getId());
    assertTrue(!userOptional1.isPresent());


  }

}
