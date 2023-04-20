package com.rappi.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.utils.composable
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.movie.api.MovieEntry
import com.rappi.navigation.Destinations
import com.rappi.navigation.entry
import dagger.android.AndroidInjection
import javax.inject.Inject

@OptIn(
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
            startDestination = destinations.entry<MovieEntry>().destination.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(destinations.entry<MovieEntry>().destination) {
                with(destinations.entry<MovieEntry>()) {
                    ComposableView(navController, destinations)
                }
            }

            composable(destinations.entry<MovieDetailEntry>().destination) {
                with(destinations.entry<MovieDetailEntry>()) {
                    ComposableView(navController, destinations)
                }
            }
        }
    }
}