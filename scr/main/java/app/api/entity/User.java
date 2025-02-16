package app.api.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
  // пока что telegramId и email не нужен и мы его не будет использовать
  UserId telegramId;
  String userName;
  String password;
  String email;
  List<Category> categories;
  List<Site> sites;

  public User(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }
}
