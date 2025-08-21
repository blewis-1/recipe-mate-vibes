package com.example.recipe_mate.data.model

data class RecipeListResponse(
    val recipes: List<Recipe>,
    val total: Int,
    val skip: Int,
    val limit: Int
)