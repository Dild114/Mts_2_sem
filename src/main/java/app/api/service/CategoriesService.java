package app.api.service;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.repository.CategoryRepository;
import app.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoriesService {

//  public CategoriesService(CategoryRepository categoryRepository) {
//    this.categoryRepository = categoryRepository;
//  }

  private final UserRepository userRepository;

  private final CategoryRepository categoryRepository;

  public CategoriesService(UserRepository userRepository, CategoryRepository categoryRepository) {
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }


  @Transactional
  public List<Category> findAll(UserId userId) {
    log.info("Finding all categories");
    return categoryRepository.findAllByUserId(userId.id());
  }

  @Transactional
  public Category findCategoryById(CategoryId categoryId, UserId userId) {
    log.info("findById({})", categoryId.id());
    Optional<Category> categoryOptional = categoryRepository.findById(categoryId.id());
    if (categoryOptional.isPresent()) {
      Category category = categoryOptional.get();
      if (category.getUser().getId().equals(userId.id()) ) {
        return category;
      } else {
        throw new RuntimeException("Not found");
      }
    }
    return null;
  }

  @Transactional
  @Async
  public void delete(CategoryId categoryId, UserId userId) {
    Optional<Category> categoryOptional = categoryRepository.findById(categoryId.id());
    if (categoryOptional.isPresent()) {
      Category category = categoryOptional.get();
      if (category.getUser().getId().equals(userId.id())) {
        categoryRepository.deleteById(categoryId.id());
      } else {
        throw new RuntimeException("Not found");
      }
    }
    log.info("Deleting Category with id {}", categoryId.id());
  }

  @Transactional
  public CategoryId create(String name, UserId userId) {

    Optional<User> userOptional = userRepository.findById(userId.id());
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Category new_category = new Category(name, user);
      log.info("Creating Category with name {}", name);
      Category category = categoryRepository.save(new_category);
      return new CategoryId(category.getId());
    } else {
      throw new RuntimeException("Not found");
    }
  }
}
