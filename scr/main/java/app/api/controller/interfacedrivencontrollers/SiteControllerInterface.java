package app.api.controller.interfacedrivencontrollers;

import app.api.entity.SiteId;
import app.api.entity.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "SiteControllerInterface", description = "Управление сайтами")
@RequestMapping("/site")
public interface SiteControllerInterface {

  @Operation(summary = "Получить все сайты")
  @ApiResponse(responseCode = "200", description = "Получены все сайты")
  @GetMapping("/all")
  ResponseEntity<?> getSites();

  @Operation(summary = "Получить сайты пользователя по ID")
  @ApiResponse(responseCode = "200", description = "Получены сайты пользователя")
  @ApiResponse(responseCode = "400", description = "Некорректные данные")
  @GetMapping("/{userId}")
  ResponseEntity<?> mySites(
      @Parameter(description = "ID пользователя", required = true)
      @PathVariable int userId
  );

  @Operation(summary = "Добавить сайт пользователю")
  @ApiResponse(responseCode = "200", description = "Добавлен сайт пользователю")
  @ApiResponse(responseCode = "400", description = "Некорректные данные")
  @PatchMapping("/{siteId}")
  ResponseEntity<Void> addSite(
      @Parameter(description = "ID сайта", required = true)
      @PathVariable int siteId,

      @Parameter(description = "ID пользователя", required = true)
      @RequestBody UserId userId
  );

  @Operation(summary = "Удалить сайт пользователя")
  @ApiResponse(responseCode = "200", description = "Сайт пользователя удален")
  @ApiResponse(responseCode = "404", description = "Сайт не найден")
  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteSite(
      @Parameter(description = "ID сайта", required = true)
      @PathVariable int id,

      @Parameter(description = "ID пользователя", required = true)
      @RequestBody UserId userId
  );
}
