package app.api.repository;

import app.api.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

  Optional<Site> findById(Long siteId);
  // получение просто сайтов без их категорий
  List<Site> findSiteByUserId(Long userId);

  void deleteSiteById(Long siteId);
  void deleteAllByUserId(Long userId);
}
