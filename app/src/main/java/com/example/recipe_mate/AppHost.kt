package com.example.recipe_mate

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.recipe_mate.ui.screens.FavoritesScreen
import com.example.recipe_mate.ui.screens.RecipeDetailScreen
import com.example.recipe_mate.ui.screens.RecipeListScreen
import com.example.recipe_mate.ui.viewmodel.RecipeViewModel
import kotlinx.serialization.Serializable


@Serializable
object RecipeListRoute

@Serializable
data class RecipeDetailRoute(val recipeId: Int)

@Serializable
object FavoritesRoute

@Composable
fun AppHost(
    modifier: Modifier = Modifier,
    recipeViewModel: RecipeViewModel
) {
    val navController = rememberNavController()


    NavHost(
        navController,
        startDestination = RecipeListRoute,
        modifier = modifier,
    ) {
        composable<RecipeListRoute> {
            RecipeListScreen(
                recipeViewModel = recipeViewModel,
                onNavigate = { recipeId ->
                    recipeViewModel.getSingleRecipe(recipeId)
                    navController.navigate(RecipeDetailRoute(recipeId))
                },
                onTappedFavorites = {
                    navController.navigate(FavoritesRoute)
                }
            )
        }

        composable<RecipeDetailRoute> { backStackEntry ->
            val recipeID = backStackEntry.toRoute<RecipeDetailRoute>().recipeId
            RecipeDetailScreen(
                modifier, recipeID,
                {
                    navController.popBackStack()
                },
                recipeViewModel,
            )
        }

        composable<FavoritesRoute> {
            FavoritesScreen(recipeViewModel = recipeViewModel, onNavigateBack = {
                navController.popBackStack()
            })
        }
    }
}