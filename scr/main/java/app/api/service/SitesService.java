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
public class SitesService {
  public SiteRepository siteRepository;

  public SitesService(SiteRepository siteRepository) {
    this.siteRepository = siteRepository;
  }

  public List<Site> getSites(UserId userId) {
    List<Site> sites = siteRepository.findAllSite(userId);
    log.info("Get sites");
    return sites;
  }
  public void deleteSite(SiteId siteId, UserId userId) {
    siteRepository.deleteSiteById(siteId, userId);
    log.info("Delete site {}", siteId);
  }
  public void addSite(SiteId siteId, UserId userId) {
    siteRepository.add(siteId, userId);
    log.info("Add site {}", siteId);
  }
}
