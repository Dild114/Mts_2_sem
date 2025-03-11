package app.api.service;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.repository.SiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SitesService {
  private final Set<Long> processedIds = ConcurrentHashMap.newKeySet();
  private final SiteRepository siteRepository;

  public SitesService(SiteRepository siteRepository) {
    this.siteRepository = siteRepository;
  }

  @Cacheable(value = "Sites", key = "#userId")
  public List<Site> getSites(UserId userId) {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    List<Site> sites = siteRepository.findAllSite(userId);
    log.info("Get sites");
    return sites;
  }

  @CachePut(value = "Sites", key = "#userId")
  public void deleteSite(SiteId siteId, UserId userId) {
    siteRepository.deleteSiteById(siteId, userId);
    log.info("Delete site {}", siteId);
  }

  // Чтобы у пользователя не было дубликатов сайта
  @CachePut(value = "Sites", key = "#userId")
  public void addSite(SiteId siteId, UserId userId) {
    if (!processedIds.add(userId.id())) {
      log.info("Already processed");
    }
    siteRepository.add(siteId, userId);
    log.info("Add site {}", siteId);
  }
}
