package app.api.repository;

import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.UserId;

import java.util.List;

public class DummyCategoryRepository implements CategoryRepository {
  @Override
  public CategoryId getCategoryId() {
    return null;
  }

  @Override
  public List<Category> findAll(UserId userId) {
    return List.of();
  }

  @Override
  public Category findById(CategoryId id) {
    return null;
  }

  @Override
  public void delete(CategoryId id, UserId userId) {

  }

  @Override
  public boolean create(Category category) {
    return false;
  }
}
