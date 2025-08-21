package com.example.recipe_mate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.recipe_mate.ui.theme.RecipemateTheme
import com.example.recipe_mate.ui.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        No go zone for scale...
        val recipeViewModel: RecipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            RecipemateTheme {
                Surface {
                    AppHost(modifier = Modifier.padding(), recipeViewModel)
                }
            }
        }
    }
}

