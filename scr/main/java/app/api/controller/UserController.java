package app.api.controller;

import app.api.entity.UserId;
import app.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
  public final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public String createUser(
      @RequestParam String name,
      @RequestParam String password,
      Model model) {
    if (name.isEmpty() || password.isEmpty()) {
      model.addAttribute("error", "Name or password cannot be empty.");
      return "error";
    }
    try {
      UserId userId = userService.createUser(name, password);
      model.addAttribute("id", userId);
      return "register";
    } catch (Exception e) {
      model.addAttribute("error", "Create user failed.");
      return "error";
    }
  }

  @DeleteMapping("/signup/:id")
  public String deleteUser(@RequestParam int id, Model model) {
    UserId userId = new UserId(id);
    try {
      userService.deleteUser(new UserId(id));
      model.addAttribute("id", userId);
      return "deleteUsers";
    } catch (Exception e) {
      model.addAttribute("error", "Delete user failed.");
      return "error";
    }
  }
}