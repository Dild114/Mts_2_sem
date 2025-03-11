package app.api.service;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll(UserId userId) {
    log.info("Finding all categories");
    return categoryRepository.findAll(userId);
  }
  public Category findById(CategoryId id) {
    log.info("findById({})", id);
   return null;
  }
  public void delete(CategoryId id, UserId userId) {
    log.info("Deleting Category with id {}", id);
  }

  public CategoryId create(String name, UserId userId) {
    log.info("Creating Category with name {}", name);
    return null;
  }
}
