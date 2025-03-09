package app.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Article", description = "Сущность пользователя")
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор", example = "123")
    private Long id;

    @NotNull
    @Schema(description = "Имя Статьи", example = "ML")
    private String title;

    @Schema(description = "текст статьи")
    private String content;

    @NotNull
    @Schema(description = "Url ссылка статьи", example = "https://habr.com/ru/articles/814061/")
    private String url;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    protected Article() {}
}
