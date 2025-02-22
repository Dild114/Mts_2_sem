package app.config.customendpointsacuators;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Endpoint(id = "customEndpoint")
public class CustomEndpoints {

  @ReadOperation
  public UUID customEndpoint() {
    return UUID.randomUUID();
  }
}
