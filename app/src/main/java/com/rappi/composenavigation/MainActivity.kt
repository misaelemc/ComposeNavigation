package com.rappi.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.movie.api.MovieEntry
import com.rappi.navigation.Destinations
import com.rappi.navigation.entry
import dagger.android.AndroidInjection
import javax.inject.Inject

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterialNavigationApi::class
)
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var destinations: Destinations

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(Modifier.fillMaxSize()) {
                    AppNavHost(it)
                }
            }
        }
    }

    @Composable
    private fun AppNavHost(paddingValues: PaddingValues) {
        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberNavController(bottomSheetNavigator)

        NavHost(
            navController,
            startDestination = destinations.entry<MovieEntry>().route
        ) {
            with(destinations.entry<MovieEntry>()) {
                composable(navController, destinations)
            }
            with(destinations.entry<MovieDetailEntry>()) {
                composable(navController, destinations)
            }
        }
    }
}