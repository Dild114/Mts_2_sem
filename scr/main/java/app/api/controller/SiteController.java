package app.api.controller;

import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.UserId;
import app.api.service.SiteService;
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

  @Autowired
  public SiteController(SiteService siteService) {
    this.siteService = siteService;
  }

  @GetMapping("all/sites")
  public String getSites(Model model) {
    HashMap<String, Integer> sites = new HashMap<>();
    int ind = 0;
    for (var site : Sites.values()) {
      sites.put(site.name(), ind++);
    }
    model.addAttribute("sites", sites);
    return "allSites";
  }

  @GetMapping("/sites")
  public String mySites(@RequestParam int userId, Model model) {
    try {
      List<Site> sites = siteService.getSites(new UserId(userId));
      model.addAttribute("sites", sites);
      return "mySites";
    } catch (Exception e) {
      model.addAttribute("error", "Get sites error");
      return "error";
    }
  }

  @PostMapping("/site/:id")
  public String addSite(@RequestParam int id, @RequestParam int userId, Model model) {
    try {
      UserId userId1 = new UserId(userId);
      siteService.addSite(new SiteId(id), userId1);
      model.addAttribute("success", "Site added successfully");
      return "addSite";
    } catch (Exception e) {
      model.addAttribute("error", "Add site failed");
      return "error";
    }
  }

  @DeleteMapping("/site/:id")
  public String deleteSite(@RequestParam int id, @RequestParam int userId, Model model) {
    try {
      UserId userId1 = new UserId(userId);
      siteService.deleteSite(new SiteId(id), userId1);
      model.addAttribute("success", "Site delete successfully");
      return "addSite";
    } catch (Exception e) {
      model.addAttribute("error", "Delete site failed");
      return "error";
    }
  }
}
