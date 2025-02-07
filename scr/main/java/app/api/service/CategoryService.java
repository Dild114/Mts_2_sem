package app.api.service;

import app.api.controller.ArticleController;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;
import app.api.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);
  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll(UserId userId) {
    return categoryRepository.findAll(userId);
  }
  public Category findById(CategoryId id) {
    LOG.info("findById({})", id);
   return null;
  }
  public void delete(CategoryId id, UserId userId) {
    LOG.info("Deleting Category with id {}", id);
  }

  public CategoryId create(String name, UserId userId) {
    LOG.info("Creating Category with name {}", name);
    return null;
  }
}
