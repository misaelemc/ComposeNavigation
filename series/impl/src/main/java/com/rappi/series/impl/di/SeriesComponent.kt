package com.rappi.series.impl.di

import com.rappi.common.AppScope
import com.rappi.common.SingleIn
import com.rappi.common.viewModel.ViewModelFactory
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo

@SingleIn(SeriesFeatureScope::class)
@ContributesSubcomponent(
    scope = SeriesFeatureScope::class,
    parentScope = AppScope::class
)
interface SeriesComponent {

    fun getFactoryViewModelAssistedFactory(): ViewModelFactory

    @ContributesTo(AppScope::class)
    interface ParentComponent {
        fun createSeriesComponent(): SeriesComponent
    }
}

abstract class SeriesFeatureScope private constructor()