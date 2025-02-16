package app.api.controller;


import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.service.CategoryService;

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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
  public CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<Category>> getMyCategories(@RequestBody long userId) {
    log.info("getMyCategories");
    List<Category> categories = categoryService.findAll(new UserId(userId));
    return ResponseEntity.ok(categories);
  }

  @PostMapping
  public ResponseEntity<?> addCategory(@RequestBody String name, @RequestBody int userId) {
    log.info("addCategory");
    try {
      CategoryId categoryId = categoryService.create(name, new UserId(userId));
      return ResponseEntity.ok(categoryId);
    } catch (Exception e) {
      log.error("addCategory failed: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable long id, @RequestBody int userId) {
    log.info("deleteCategory");
    try {
      categoryService.delete(new CategoryId(id), new UserId(userId));
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      log.error("deleteCategory failed: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping
  public ResponseEntity<?> AddCategories(@RequestBody int userId, @RequestBody String[] categories) {
    log.info("AddCategories with userId: {}", userId);

    try {
      CategoryId[] categoriesId = new CategoryId[categories.length];
      for (int i = 0; i < categories.length; i++) {
        categoriesId[i] =  categoryService.create(categories[i], new UserId(userId));
      }
      return ResponseEntity.ok(categoriesId);
    } catch (Exception e) {
      log.error("AddCategories failed", e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
