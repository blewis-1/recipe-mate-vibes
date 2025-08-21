package com.example.recipe_mate.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipe_mate.NetworkResponse
import com.example.recipe_mate.data.model.Recipe
import com.example.recipe_mate.ui.componets.Loader
import com.example.recipe_mate.ui.componets.TopAppBar
import com.example.recipe_mate.ui.viewmodel.RecipeViewModel
import androidx.compose.runtime.collectAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    recipeId: Int,
    navigateBack: ()-> Unit,
    viewModel: RecipeViewModel
) {
    val recipeState = viewModel.recipe.collectAsState().value

    when (recipeState) {
        is NetworkResponse.Error -> {
            Text("Fail to load")
        }
        NetworkResponse.Loading -> {
            Loader()
        }
        is NetworkResponse.Success<Recipe> -> {
            val recipe = recipeState.data
            Log.e("Modifier", "$modifier")
            Surface {
                Column(
                    modifier.verticalScroll(
                        rememberScrollState()
                    )
                ) {
                    TopAppBar(title = recipe.name,
                        navigateBack = {navigateBack()},
                        showBackButton = true)
                    AsyncImage(
                        recipe.image,
                        recipe.name,
                        Modifier
                            .height(400.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )

                    recipe.ingredients.forEach { ingredient ->
                        Text(ingredient)
                    }
                }
            }

        }
    }
}
