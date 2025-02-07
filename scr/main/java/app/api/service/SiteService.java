package app.api.service;

import app.api.controller.ArticleController;
import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.repository.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {
  private static final Logger LOG = LoggerFactory.getLogger(SiteService.class);
  public SiteRepository siteRepository;

  @Autowired
  public SiteService(SiteRepository siteRepository) {
    this.siteRepository = siteRepository;
  }

  public List<Site> getSites(UserId userId) {
    LOG.info("Getting sites");
    return null;
  }
  public void deleteSite(SiteId siteId, UserId userId) {
    LOG.info("Deleting site {}", siteId);
  }
  public void addSite(SiteId siteId, UserId userId) {
    LOG.info("Adding site {}", siteId);
  }
}
