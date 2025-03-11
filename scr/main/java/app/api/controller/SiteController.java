package app.api.controller;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.service.SiteService;
import app.api.controller.interfaceDrivenControllers.SiteControllerInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class SiteController implements SiteControllerInterface {

  private final SiteService siteService;

  public SiteController(SiteService siteService) {
    this.siteService = siteService;
  }

  @Override
  public ResponseEntity<?> getSites() {
    log.info("Getting all sites");
    HashMap<String, Integer> sites = new HashMap<>();
    int ind = 0;
    for (var site : Sites.values()) {
      sites.put(site.getUrl(), ind++);
    }
    return ResponseEntity.ok(sites);
  }

  @Override
  public ResponseEntity<?> mySites(int userId) {
    log.info("Getting sites for userId: {}", userId);
    List<Site> sites = siteService.getSites(new UserId(userId));
    return ResponseEntity.ok(sites);
  }

  @Override
  public ResponseEntity<?> addSite(int siteId, int userId) {
    log.info("Adding site for userId: {}", userId);
    try {
      UserId userId1 = new UserId(userId);
      siteService.addSite(new SiteId(siteId), userId1);
      return ResponseEntity.ok("site added with id: " + siteId);
    } catch (Exception e) {
      log.error("Adding site failed: ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<?> deleteSite(int siteId, int userId) {
    log.info("Deleting site for userId: {}", userId);
    try {
      UserId userId1 = new UserId(userId);
      siteService.deleteSite(new SiteId(siteId), userId1);
      return ResponseEntity.ok("site deleted with id: " + siteId);
    } catch (Exception e) {
      log.error("Deleting site failed", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
