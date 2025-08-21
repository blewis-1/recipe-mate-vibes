package com.example.recipe_mate.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipe_mate.data.model.Recipe
import com.example.recipe_mate.ui.componets.TopAppBar
import com.example.recipe_mate.ui.viewmodel.RecipeViewModel


@Composable
fun FavoritesScreen(
    recipeViewModel: RecipeViewModel,
    onNavigateBack: () -> Unit
) {
    val favoriteRecipes = recipeViewModel.favoriteRecipes.collectAsState().value

    Column {
        TopAppBar(
            title = "Favorite Recipes",
            showBackButton = true,
            navigateBack = {
                onNavigateBack()
            })
        if (favoriteRecipes.isEmpty()) {
            Text("Add Recipes to Favorite")
        } else {
            LazyColumn {
                items(favoriteRecipes) { recipe ->
                    FavoriteItem(
                        recipe = recipe,
                        onDeleteFavorite = {
                            recipeViewModel.deleteFavorite(recipe)
                        })
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    onDeleteFavorite: (recipe: Recipe) -> Unit
) {

    Column(
        modifier.padding(16.dp)
    ) {
        AsyncImage(
            recipe.image,
            recipe.name,
            Modifier
                .height(150.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )

        Text(recipe.name)
        Text(recipe.difficulty)
        Button(onClick = {
            onDeleteFavorite(recipe)
        }) {
            Text("Delete Favorite")
        }
    }
}


