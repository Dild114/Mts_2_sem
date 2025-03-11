package app.api.controller.interfaceDrivenControllers;

import app.api.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CategoryControllerInterface", description = "Управление категориями")
@RequestMapping("/category")
public interface CategoryControllerInterface {

  @Operation(summary = "Получить категории пользователя")
  @ApiResponse(responseCode = "200", description = "Получены категории пользователя")
  @GetMapping
  ResponseEntity<List<Category>> getMyCategories(
      @Parameter(description = "ID пользователя", required = true)
      @RequestBody long userId
  );

  @Operation(summary = "Добавить категорию пользователю")
  @ApiResponse(responseCode = "200", description = "Добавлена категория")
  @ApiResponse(responseCode = "400", description = "Некорректные данные")
  @PostMapping
  ResponseEntity<?> addCategory(
      @Parameter(description = "Имя категории", required = true)
      @RequestBody String name,

      @Parameter(description = "ID пользователя", required = true)
      @RequestBody int userId
  );

  @Operation(summary = "Удалить категорию пользователю по ID")
  @ApiResponse(responseCode = "200", description = "Категория пользователя удалена")
  @ApiResponse(responseCode = "404", description = "Категория не найдена")
  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteCategory(
      @Parameter(description = "ID категории", required = true)
      @PathVariable long id,

      @Parameter(description = "ID пользователя", required = true)
      @RequestBody int userId
  );

  @Operation(summary = "Добавить категории пользователю")
  @ApiResponse(responseCode = "200", description = "Добавлены категории пользователю")
  @ApiResponse(responseCode = "400", description = "Некорректные данные")
  @PutMapping
  ResponseEntity<?> addCategories(
      @Parameter(description = "ID пользователя", required = true)
      @RequestBody int userId,

      @Parameter(description = "Массив категорий", required = true)
      @RequestBody String[] categories
  );
}
