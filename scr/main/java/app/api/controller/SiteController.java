package app.api.controller;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.service.SiteService;
import app.api.service.UserService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class SiteController {
  public final SiteService siteService;
  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public SiteController(SiteService siteService) {
    this.siteService = siteService;
  }

  @GetMapping("/all/sites")
  public String getSites(Model model) {
    LOG.info("Getting sites");
    HashMap<String, Integer> sites = new HashMap<>();
    int ind = 0;
    for (var site : Sites.values()) {
      sites.put(site.getUrl(), ind++);
    }
    model.addAttribute("sites", sites);
    return "allSites";
  }

  @GetMapping("/sites")
  public String mySites(@RequestParam int userId, Model model) {
    LOG.info("Getting sites");
    try {
      List<Site> sites = siteService.getSites(new UserId(userId));
      model.addAttribute("sites", sites);
      return "mySites";
    } catch (Exception e) {
      LOG.error("Getting sites failed", e);
      model.addAttribute("error", "Get sites error");
      return "error";
    }
  }

  @PostMapping("/site")
  public String addSite(@RequestParam int id, @RequestParam int userId, Model model) {
    LOG.info("Adding site");
    try {
      UserId userId1 = new UserId(userId);
      siteService.addSite(new SiteId(id), userId1);
      model.addAttribute("success", "Site added successfully");
      return "addSite";
    } catch (Exception e) {
      LOG.error("Adding site failed", e);
      model.addAttribute("error", "Add site failed");
      return "error";
    }
  }

  @DeleteMapping("/site")
  public String deleteSite(@RequestParam int id, @RequestParam int userId, Model model) {
    LOG.info("Deleting site");
    try {
      UserId userId1 = new UserId(userId);
      siteService.deleteSite(new SiteId(id), userId1);
      model.addAttribute("success", "Site delete successfully");
      return "addSite";
    } catch (Exception e) {
      LOG.error("Deleting site failed", e);
      model.addAttribute("error", "Delete site failed");
      return "error";
    }
  }
}
