package app.aop;


import app.config.aop.LoggingAspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AopTest {

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private LoggingAspect loggingAspect;

  @BeforeEach
  public void resetCounter() {
    loggingAspect.resetCounter();
  }

  @LocalServerPort
  private int port;

  @Test
  public void test() {
    assertEquals(0, loggingAspect.getCounter());
    restTemplate.getForEntity("http://localhost:" + port + "/site/all", HashMap.class);
    assertEquals(2, loggingAspect.getCounter());
  }
}
