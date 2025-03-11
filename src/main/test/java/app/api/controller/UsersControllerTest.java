package app.api.controller;


import app.Application;
import app.api.entity.User;
import app.api.entity.UserId;
import app.api.service.UsersService;
import app.config.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;


@WebMvcTest(UsersController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {Application.class, SecurityConfig.class })
class UsersControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UsersService usersService;



  @Test
  void createUser() throws Exception {
    User mockUser = new User("testName", "testPassword" );
    UsersRequest mockUsersRequest = new UsersRequest("testName", "testPassword");
    mockUser.setId(1L);
    when(usersService.createUser("testName", "testPassword")).thenReturn(new UserId(mockUser.getId()));
    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"testName\", \"password\": \"testPassword\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));

  }

  @Test
  void deleteUser() throws Exception {
    User mockUser = new User("testName", "testPassword" );
    mockUser.setId(1L);

    when(usersService.createUser("testName", "testPassword")).thenReturn(new UserId(mockUser.getId()));

    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"testName\", \"password\": \"testPassword\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));

    mockMvc.perform(delete("/signup/1")).andExpect(status().isNoContent());
  }

  @Test
  void updateUser() throws Exception {
    User mockUser = new User("testName", "testPassword" );
    mockUser.setId(1L);
    when(usersService.createUser("testName", "testPassword")).thenReturn(new UserId(mockUser.getId()));

    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"testName\", \"password\": \"testPassword\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));
    mockMvc.perform(patch("/signup/1")
        .contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"newTestName\"}"))
        .andExpect(status().isNoContent());
  }

  @Test
  void createUserException() throws Exception {
    User mockUser = new User(null, "testPassword" );
    mockUser.setId(1L);
    mockMvc.perform(post("/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"\", \"password\": \"testPassword\"}"))
        .andExpect(status().isBadRequest());
  }
}
