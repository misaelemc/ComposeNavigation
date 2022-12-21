package com.rappi.composenavigation.di

import androidx.lifecycle.ViewModel
import com.rappi.common.AppScope
import com.rappi.common.ViewModelKey
import com.slack.circuit.CircuitConfig
import com.slack.circuit.Presenter
import com.slack.circuit.Ui
import com.slack.circuit.backstack.BackStackRecordLocalProviderViewModel
import com.slack.circuit.retained.Continuity
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds

@ContributesTo(AppScope::class)
@Module
abstract class CircuitModule {

    @Multibinds
    abstract fun presenterFactories(): Set<Presenter.Factory>

    @Multibinds
    abstract fun viewFactories(): Set<Ui.Factory>

    @ViewModelKey(BackStackRecordLocalProviderViewModel::class)
    @Binds
    @IntoMap
    abstract fun bindBackStackRecordLocalProviderViewModel(backStackRecordLocalProviderViewModel: BackStackRecordLocalProviderViewModel): ViewModel

    @ViewModelKey(Continuity::class)
    @IntoMap
    @Binds
    abstract fun bindContinuity(continuity: Continuity): Continuity

    companion object {

        @Provides
        fun provideBackStackRecordProviderViewModel(): BackStackRecordLocalProviderViewModel {
            return BackStackRecordLocalProviderViewModel()
        }

        @Provides
        fun provideContinuity(): Continuity {
            return Continuity()
        }


        @Provides
        fun provideCircuit(
            presenterFactories: @JvmSuppressWildcards Set<Presenter.Factory>,
            uiFactories: @JvmSuppressWildcards Set<Ui.Factory>,
        ): CircuitConfig {
            return CircuitConfig.Builder()
                .apply {
                    for (factory in presenterFactories) {
                        addPresenterFactory(factory)
                    }
                    for (factory in uiFactories) {
                        addUiFactory(factory)
                    }
                }
                .build()
        }
    }
}