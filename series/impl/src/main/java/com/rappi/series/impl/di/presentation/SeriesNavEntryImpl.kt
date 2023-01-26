package com.rappi.series.impl.di.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.rappi.common.AppScope
import com.rappi.common.ComponentHolder
import com.rappi.common.viewModel.compose.InjectComposition
import com.rappi.common.viewModel.compose.daggerViewModel
import com.rappi.navigation.FeatureEntry
import com.rappi.navigation.FeatureEntryKey
import com.rappi.navigation.NavDestinations
import com.rappi.series.SeriesEntry
import com.rappi.series.impl.di.SeriesComponent
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class, boundType = FeatureEntry::class)
@FeatureEntryKey(SeriesEntry::class)
class SeriesNavEntryImpl @Inject constructor() : SeriesEntry() {

    @ExperimentalMaterialApi
    @Composable
    override fun NavGraphBuilder.Composable(
        navController: NavHostController,
        destinations: NavDestinations,
        backStackEntry: NavBackStackEntry,
        sheetState: ModalBottomSheetState
    ) {
        val component =
            ComponentHolder.component<SeriesComponent.ParentComponent>().createSeriesComponent()

        InjectComposition(component.getFactoryViewModelAssistedFactory()) {
            SeriesScreen(
                viewModel = daggerViewModel(),
            )
        }
    }

}