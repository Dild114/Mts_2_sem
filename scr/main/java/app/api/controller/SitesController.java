package app.api.controller;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.service.SitesService;
import app.api.controller.interfacedrivencontrollers.SiteControllerInterface;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class SitesController implements SiteControllerInterface {
  private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("apiCircuitBreaker");
  private final RateLimiter rateLimiter = RateLimiter.ofDefaults("apiRateLimiter");

  private final SitesService sitesService;

  public SitesController(SitesService sitesService) {
    this.sitesService = sitesService;
  }

  @Override
  public ResponseEntity<HashMap<String, Integer>> getSites() {
    log.info("Getting all sites");
    HashMap<String, Integer> sites = new HashMap<>();
    int ind = 0;
    for (var site : Sites.values()) {
      sites.put(site.getUrl(), ind++);
    }
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> ResponseEntity.status(HttpStatus.OK).body(sites)));
  }

  @Override
  public ResponseEntity<List<Site>> mySites(User user) {
    log.info("Getting sites for userId: {}", user.getId());
    List<Site> sites = sitesService.getSites(new UserId(userId));
    return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> ResponseEntity.ok(sites)));
  }

  @Override
  public ResponseEntity<Void> addSite(String url, User user) {
    log.info("Adding site for userId: {}", user.getId());
    try {
      sitesService.addSite(new Site(url, user));
      return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> ResponseEntity.status(HttpStatus.CREATED)).build());
    } catch (Exception e) {
      log.error("Adding site failed: ", e);
      return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }
  }

  @Override
  public ResponseEntity<Integer> deleteSite(int siteId, UserId userId) {
    log.info("Deleting site for userId: {}", userId);
    try {
      sitesService.deleteSite(new SiteId(siteId), userId);
      return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> ResponseEntity.ok(siteId)));
    } catch (Exception e) {
      log.error("Deleting site failed", e);
      return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }
  }
}
