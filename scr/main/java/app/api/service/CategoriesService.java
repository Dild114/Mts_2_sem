package app.api.service;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoriesService {
  private final CategoryRepository categoryRepository;

  public CategoriesService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll(UserId userId) {
    log.info("Finding all categories");
    return categoryRepository.findAll(userId);
  }
  public Category findById(CategoryId id) {
    Category category = categoryRepository.findById(id);
    log.info("findById({})", id);
   return category;
  }

  @Async
  public void delete(CategoryId id, UserId userId) {
    categoryRepository.delete(id, userId);
    log.info("Deleting Category with id {}", id);
  }

  public CategoryId create(String name, UserId userId) {
    CategoryId categoryId = categoryRepository.getCategoryId();
    Category category = new Category(categoryId, name, userId);
    categoryRepository.create(category);
    log.info("Creating Category with name {}", name);
    return categoryId;
  }
}
