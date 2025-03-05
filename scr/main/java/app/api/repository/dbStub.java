package app.api.repository;

import app.api.entity.Article;
import app.api.entity.ArticleId;
import app.api.entity.Category;
import app.api.entity.CategoryId;
import app.api.entity.Site;
import app.api.entity.SiteId;
import app.api.entity.User;
import app.api.entity.UserId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class dbStub implements DbRepository {
  private final List<Article> articles = new ArrayList<>();
  private final List<Category> categories = new ArrayList<>();
  private final List<Site> sites = new ArrayList<>();
  private final Map<Integer, User> users = new HashMap();
  private int articleIdCounter = 0;
  private int categoryIdCounter = 0;
  private int siteIdCounter = 0;
  private int userIdCounter = 0;

  @Override
  public ArticleId generateIdArticle() {
    return new ArticleId(articleIdCounter++);
  }

  @Override
  public List<Article> getArticles(UserId userId) {

    // наверное тут буду делать запрос на парсер и мл а потом мы добавляем артиклы
    return articles;
  }

  @Override
  public CategoryId generateIdCategory() {
    return new CategoryId(categoryIdCounter++);
  }

  @Override
  public List<Category> findAllCategory(UserId userId) {
    List<Category> answerCategory = new ArrayList<>();
    for (Category category : categories) {
      if (category.userId().equals(userId)) {
        answerCategory.add(category);
      }
    }
    return answerCategory;
  }

  @Override
  public Category findCategoryById(CategoryId id) {
    return categories.stream()
        .filter(category -> category.id().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public boolean addCategory(Category category) {
    return categories.add(category);
  }

  @Override
  public boolean deleteCategory(CategoryId id, UserId userId) {
    for (Category category : categories) {
      if (category.userId().equals(userId)) {
        categories.remove(category);
        return true;
      }
    }
    return false;
  }

  @Override
  public SiteId generateIdSite() {
    return new SiteId(siteIdCounter++);
  }

  @Override
  public void deleteSiteById(SiteId id, UserId userId) {
    for (Site site : sites) {
      if (site.userId().equals(userId)) {
        sites.remove(site);
      }
    }
  }

  @Override
  public void add(SiteId siteId, UserId userId) {

  }

  @Override
  public void addSite(Site site) {
    sites.add(site);
  }

  @Override
  public SiteId getSiteId(Site site) {
    return null;
  }

  @Override
  public List<Site> findAllSite(UserId userId) {
    List<Site> answerSites = new ArrayList<>();
    for (Site site : sites) {
      if (site.userId().equals(userId)) {
        answerSites.add(site);
      }
    }
    return answerSites;
  }

  @Override
  public Site findSiteById(SiteId id) {
    return sites.stream()
        .filter(site -> site.id().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public UserId generateUserId() {
    return new UserId(userIdCounter++);
  }

  @Override
  public UserId createUser (User user) {
    userIdCounter++;
    user.setUserId(new UserId(userIdCounter));
    users.put(userIdCounter, user);
    return new UserId(userIdCounter);
  }

  @Override
  public void deleteUser (UserId userId) {
    for (Category category : categories) {
      if (category.userId().equals(userId)) {
        categories.remove(category);
      }
    }

    for (Site site : sites) {
      if (site.userId().equals(userId)) {
        sites.remove(site);
      }
    }
    try  {
      users.remove(userId.id());
    } catch (Exception e) {
      throw new NoSuchElementException(e);
    }
  }

  @Override
  public void updateUser(UserId userId, User user) {
    users.put(userId.id(), user);
  }

  @Override
  public void updateNameUser(UserId userID, String newName) {

  }

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
  public CategoryId delete(CategoryId id, UserId userId) {
    return null;
  }

  @Override
  public boolean create(Category category) {
    return false;
  }

  @Override
  public UserId generateId() {
    return null;
  }
}