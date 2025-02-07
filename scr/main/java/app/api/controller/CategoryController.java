package app.api.controller;


import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {
  public CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/categories")
  public String getMyCategories(@RequestParam int userId, Model model) {
    try {
      List<Category> categories = categoryService.findAll(new UserId(userId));
      model.addAttribute("categories", categories);
      return "Mycategories";
    } catch (Exception e) {
      model.addAttribute("error", "getMyCategories failed");
      return "error";
    }
  }

  @PostMapping("/category")
  public String addCategory(@RequestParam String name, @RequestParam int userId, Model model) {
    try {
      categoryService.create(name, new UserId(userId));
      model.addAttribute("categories", categoryService.findAll(new UserId(userId)));
      return "Mycategories";
    } catch (Exception e) {
      model.addAttribute("error", "addCategory failed");
      return "error";
    }
  }

  @DeleteMapping("/category/:id")
  public String deleteCategory(@RequestParam int id, @RequestParam int userId, Model model) {
    try {
      categoryService.delete(new CategoryId(id), new UserId(userId));
      model.addAttribute("categories", categoryService.findAll(new UserId(userId)));
      return "Mycategories";
    } catch (Exception e) {
      model.addAttribute("error", "deleteCategory failed");
      return "error";
    }
  }
}
