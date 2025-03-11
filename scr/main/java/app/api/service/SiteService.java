package app.api.service;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.repository.SiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SiteService {
  public SiteRepository siteRepository;

  public SiteService(SiteRepository siteRepository) {
    this.siteRepository = siteRepository;
  }

  public List<Site> getSites(UserId userId) {
    log.info("Get sites");
    return null;
  }
  public void deleteSite(SiteId siteId, UserId userId) {
    log.info("Delete site {}", siteId);
  }
  public void addSite(SiteId siteId, UserId userId) {
    log.info("Add site {}", siteId);
  }
}
