package com.rappi.featureA.impl.di

import com.rappi.common.AppScope
import com.rappi.common.FeatureScope
import com.rappi.common.SingleIn
import com.rappi.featureA.impl.FeatureAViewModel
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@SingleIn(FeatureScope::class)
@ContributesSubcomponent(
    scope = FeatureScope::class,
    parentScope = AppScope::class
)
interface FeatureAComponent {

    val viewModel: FeatureAViewModel

    @ContributesTo(AppScope::class)
    interface ParentComponent {
        fun factory(): FeatureAComponent
    }
}

@Module
@ContributesTo(FeatureScope::class)
object FeatureModule {

    @Provides
    fun provideFeatureViewModel(): FeatureAViewModel = FeatureAViewModel()
}