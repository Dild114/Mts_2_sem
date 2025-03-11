package app.api.controller;

import app.api.entity.UserId;

public record CategoriesRequest(
    String name,
    UserId userId
) {}
