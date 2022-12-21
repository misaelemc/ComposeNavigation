package com.rappi.composenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.rappi.movie.impl.presentation.presenter.MovieListScreen
import com.rappi.navigation.NavDestinations
import com.slack.circuit.CircuitCompositionLocals
import com.slack.circuit.CircuitConfig
import com.slack.circuit.NavigableCircuitContent
import com.slack.circuit.Screen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.push
import com.slack.circuit.rememberCircuitNavigator
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : ComponentActivity() {


    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var circuitConfig: CircuitConfig

    @Inject
    lateinit var destinations: NavDestinations

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        var backstack: List<Screen> = listOf(MovieListScreen)
        setContent {
            MaterialTheme {

                Scaffold(Modifier.fillMaxSize()) {
                    val backstack =
                        rememberSaveableBackStack { backstack.forEach { screen -> push(screen) } }

                    val circuitNavigator = rememberCircuitNavigator(backstack = backstack)

                    CircuitCompositionLocals(circuitConfig = circuitConfig) {
                        NavigableCircuitContent(circuitNavigator, backstack)
                    }
                }
            }
        }
    }
}