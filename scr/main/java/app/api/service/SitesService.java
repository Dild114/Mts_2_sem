package app.api.service;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.repository.SiteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SitesService {
  private final Set<Integer> processedIds = ConcurrentHashMap.newKeySet();
  private final SiteRepository siteRepository;

  public SitesService(SiteRepository siteRepository) {
    this.siteRepository = siteRepository;
  }

  @Transactional
  @Cacheable(value = "Sites", key = "#userId")
  public List<Site> getSites(UserId userId) {
    List<Site> sites = siteRepository.findSiteByUserId(userId.id());
    log.info("Get sites");
    return sites;
  }

  @Transactional
  @CachePut(value = "Sites", key = "#siteid")
  public void deleteSite(SiteId siteId, UserId userId) {
    Optional<Site> siteOptional = siteRepository.findById(siteId.siteId());
    if (siteOptional.isPresent()) {
      Site site = siteOptional.get();
      if (site.getUser().getId().equals(userId.id())) {
        siteRepository.deleteSiteById(siteId.siteId());
      } else {
        throw new RuntimeException("User not allowed to delete site");
      }
    }

    log.info("Delete site {}", siteId);
  }

  // Чтобы у пользователя не было дубликатов сайта
  @Transactional
  @CachePut(value = "Sites", key = "#site.id")
  public void addSite(Site site) {
    siteRepository.save(site);
    log.info("Already processed");
    log.info("Add site {}", site.getId());
  }
}
