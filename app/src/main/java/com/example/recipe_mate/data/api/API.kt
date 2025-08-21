package com.example.recipe_mate.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object API {

    const val BASE_URL = "https://dummyjson.com/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val recipeApiService: RecipeApiService = retrofit.create(RecipeApiService::class.java)
}
