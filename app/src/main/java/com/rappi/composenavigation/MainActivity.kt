package com.rappi.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.rappi.featureA.api.FeatureAEntry
import com.rappi.featureB.api.FeatureBEntry
import com.rappi.featureC.api.FeatureCEntry
import com.rappi.navigation.NavDestinations
import com.rappi.navigation.entry
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

        ModalBottomSheetLayout(bottomSheetNavigator) {
            NavHost(
                navController,
                startDestination = destinations.entry<FeatureAEntry>().featureRoute
            ) {
                with(destinations.entry<FeatureAEntry>()) {
                    composable(navController, destinations, sheetState)
                }
                with(destinations.entry<FeatureBEntry>()) {
                    composable(navController, destinations, sheetState)
                }
                with(destinations.entry<FeatureCEntry>()) {
                    navigation(navController, destinations, sheetState)
                }
            }
        }
    }
}