package com.example.recipe_mate.ui.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe_mate.NetworkResponse
import com.example.recipe_mate.data.api.API
import com.example.recipe_mate.data.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val _recipes =
        MutableStateFlow<NetworkResponse<List<Recipe>>>(value = NetworkResponse.Loading)
    val recipes: StateFlow<NetworkResponse<List<Recipe>>> = _recipes
    private val _recipe = MutableStateFlow<NetworkResponse<Recipe>>(value = NetworkResponse.Loading)
    val recipe: StateFlow<NetworkResponse<Recipe>> = _recipe

    private val _favoriteRecipes = MutableStateFlow<List<Recipe>>(listOf())
    val favoriteRecipes: StateFlow<List<Recipe>> = _favoriteRecipes
    val _favCount: StateFlow<Int> = favoriteRecipes.map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0
        )


    init {
        getRecipes()
    }

    fun getRecipes() {
        viewModelScope.launch {
            try {
                val response = API.recipeApiService.getAllRecipes()
                _recipes.value = NetworkResponse.Success(response.recipes)
            } catch (e: Exception) {
                _recipes.value = NetworkResponse.Error(exception = e)
            }
        }
    }

    fun getSingleRecipe(recipeId: Int) {
        viewModelScope.launch {
            _recipe.value = NetworkResponse.Loading
            try {
                val response = API.recipeApiService.getSingleRecipe(recipeId)
                _recipe.value = NetworkResponse.Success(response)
            } catch (e: Exception) {
                _recipe.value = NetworkResponse.Error(exception = e)
            }
        }
    }

    fun addToFavorites(recipe: Recipe) {
        val currentRecipes = _favoriteRecipes.value.toMutableList()
        if (!currentRecipes.contains(recipe)) {
            currentRecipes.add(recipe)
            _favoriteRecipes.value = currentRecipes
        }
    }

    fun deleteFavorite(recipe: Recipe) {
        val currentRecipes = _favoriteRecipes.value.toMutableList()
        _favoriteRecipes.value = currentRecipes.filterNot { it.id == recipe.id }
    }


}