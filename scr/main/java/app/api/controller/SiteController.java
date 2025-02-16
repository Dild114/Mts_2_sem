package app.api.controller;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.service.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequestMapping("/site")
@RestController
public class SiteController {
  public final SiteService siteService;


  public SiteController(SiteService siteService) {
    this.siteService = siteService;
  }

  @GetMapping("/all")
  public ResponseEntity<?> getSites() {
    log.info("Getting all sites");
    HashMap<String, Integer> sites = new HashMap<>();
    int ind = 0;
    for (var site : Sites.values()) {
      sites.put(site.getUrl(), ind++);
    }
    return ResponseEntity.ok(sites);
  }

  @GetMapping
  public ResponseEntity<?> mySites(@RequestBody int userId) {
    log.info("Getting sites");
    List<Site> sites = siteService.getSites(new UserId(userId));
    return ResponseEntity.ok(sites);
  }

  @PatchMapping ("/{id}")
  public ResponseEntity<?> addSite(@PathVariable int id, @RequestBody int userId) {
    log.info("Adding site");
    try {
      UserId userId1 = new UserId(userId);
      siteService.addSite(new SiteId(id), userId1);
      return ResponseEntity.ok("site added with id: " + id);
    } catch (Exception e) {
      log.error("Adding site failed: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteSite(@PathVariable int id, @RequestBody int userId) {
    log.info("Deleting site");
    try {
      UserId userId1 = new UserId(userId);
      siteService.deleteSite(new SiteId(id), userId1);
      return ResponseEntity.ok("site deleted with id: " + id);
    } catch (Exception e) {
      log.error("Deleting site failed", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
