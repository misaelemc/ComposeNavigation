package com.rappi.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.rappi.movie.api.MovieEntry
import com.rappi.detail.api.MovieDetailEntry
import com.rappi.featureC.api.FeatureCEntry
import com.rappi.navigation.NavDestinations
import com.rappi.navigation.entry
import com.rappi.series.SeriesEntry
import dagger.android.AndroidInjection
import javax.inject.Inject

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterialNavigationApi::class
)
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var destinations: NavDestinations

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(Modifier.fillMaxSize()) {
                    AppNavHost()
                }
            }
        }
    }

    @Composable
    private fun AppNavHost() {
        val bottomSheetNavigator = rememberBottomSheetNavigator()
        val navController = rememberNavController(bottomSheetNavigator)

        val sheetState = rememberModalBottomSheetState(
            ModalBottomSheetValue.Hidden,
            SwipeableDefaults.AnimationSpec,
        )

        val topDestinations = listOf(
            destinations.entry<MovieEntry>(),
            destinations.entry<SeriesEntry>(),
        )

        ModalBottomSheetLayout(bottomSheetNavigator) {
            Scaffold(
                bottomBar = {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        topDestinations.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                                label = { Text(screen.getRoute()) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.getRoute() } == true,
                                onClick = {
                                    navController.navigate(screen.getRoute()) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = destinations.entry<MovieEntry>().getRoute()
                ) {
                    with(destinations.entry<MovieEntry>()) {
                        composable(navController, destinations, sheetState)
                    }
                    with(destinations.entry<MovieDetailEntry>()) {
                        composable(navController, destinations, sheetState)
                    }
                    with(destinations.entry<FeatureCEntry>()) {
                        navigation(navController, destinations, sheetState)
                    }
                    with(destinations.entry<SeriesEntry>()) {
                        composable(navController, destinations, sheetState)
                    }
                }
            }
        }
    }

}