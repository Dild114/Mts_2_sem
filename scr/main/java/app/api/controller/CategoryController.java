package app.api.controller;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.service.CategoryService;
import app.api.controller.interfaceDrivenControllers.CategoryControllerInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CategoryController implements CategoryControllerInterface {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Override
  public ResponseEntity<List<Category>> getMyCategories(UserId userId) {
    log.info("Fetching categories for userId: {}", userId);
    List<Category> categories = categoryService.findAll(userId);
    return ResponseEntity.ok(categories);
  }

  @Override
  public ResponseEntity<?> addCategory(CategoryRequest categoryRequest) {
    log.info("Add category for userId: {}", categoryRequest.userId());
    try {
      CategoryId categoryId = categoryService.create(categoryRequest.name(), categoryRequest.userId());
      return ResponseEntity.status(HttpStatus.OK).body(categoryId);
    } catch (Exception e) {
      log.error("Failed to add category: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<HttpStatus> deleteCategory(int id, UserId userId) {
    log.info("Deleting category with ID: {} for userId: {}", id, userId);
    try {
      categoryService.delete(new CategoryId(id), userId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      log.error("Failed to delete category: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @Override
  public ResponseEntity<?> addCategories(int userId, String[] categories) {
    log.info("Adding multiple categories for userId: {}", userId);
    try {
      CategoryId[] categoriesId = new CategoryId[categories.length];
      for (int i = 0; i < categories.length; i++) {
        categoriesId[i] = categoryService.create(categories[i], new UserId(userId));
      }
      return ResponseEntity.ok(categoriesId);
    } catch (Exception e) {
      log.error("Failed to add categories: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
