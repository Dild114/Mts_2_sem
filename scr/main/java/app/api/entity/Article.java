package app.api.entity;

public record Article(String name, ArticleId id, String url, CategoryId categoryId){}
