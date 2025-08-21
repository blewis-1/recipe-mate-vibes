package com.example.recipe_mate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipe_mate.NetworkResponse
import com.example.recipe_mate.data.model.Recipe
import com.example.recipe_mate.ui.componets.Loader
import com.example.recipe_mate.ui.componets.TopAppBar
import com.example.recipe_mate.ui.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    onNavigate: (recipeId: Int) -> Unit,
    recipeViewModel: RecipeViewModel,
    onTappedFavorites: () -> Unit,
) {
    val recipeState = recipeViewModel.recipes.collectAsState().value
    val favCount = recipeViewModel._favCount.collectAsState().value
    when (recipeState) {
        is NetworkResponse.Loading -> {
            Loader()
        }

        is NetworkResponse.Success -> {
            if (recipeState.data.isEmpty()) {
                Text("No Recipe Available")
            }

            Column {
                TopAppBar(
                    title = "Recipes",
                    trailingContent = {
                        BadgedBox(
                            badge = {
                                if (favCount > 0) {
                                    Badge() {
                                        Text("$favCount")
                                    }
                                }
                            },
                            modifier= Modifier.align(alignment = Alignment.CenterHorizontally)
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        onTappedFavorites()
                                    },
                                contentDescription = "Favorites Icon"
                            )
                        }
                    })
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier.padding(16.dp)
                ) {
                    items(recipeState.data) { recipe ->
                        RecipeItem(
                            modifier.clickable {
                                onNavigate(recipe.id)
                            },
                            recipe = recipe,
                            addToFavorites = {
                                recipeViewModel.addToFavorites(recipe)
                            }
                        )
                    }
                }
            }
        }

        is NetworkResponse.Error -> {
            Text("Fail to load data.")
        }
    }


}


@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    addToFavorites: (recipe: Recipe) -> Unit
) {
    Column(modifier.padding(bottom = 16.dp, end = 16.dp)) {
        Box {
            AsyncImage(
                recipe.image,
                recipe.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
            )
            Image(
                Icons.Default.Favorite,
                "Favorite Icon",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(100))
                    .background(Color.LightGray)
                    .clickable {
                        addToFavorites(recipe)
                    }
            )
        }
        Text(
            recipe.name,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 10.dp)
        )

        Row {
            recipe.mealType.forEach { meal ->
                Text(meal, style = MaterialTheme.typography.labelSmall)
            }
        }

    }
}