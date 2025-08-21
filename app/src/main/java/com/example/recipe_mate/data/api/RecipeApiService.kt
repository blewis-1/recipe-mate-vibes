package com.example.recipe_mate.data.api

import com.example.recipe_mate.data.model.Recipe
import com.example.recipe_mate.data.model.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApiService {
    @GET("recipe")
    suspend fun getAllRecipes(): RecipeListResponse

    @GET("recipes/{id}")
    suspend fun getSingleRecipe(@Path("id") id: Int ): Recipe
}