package com.rappi.common.viewModel

import com.rappi.common.AppScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module

@Module
@ContributesTo(AppScope::class)
interface ViewModelFactoryModule {

    @Binds
    fun bindsDaggerViewModelAssistedFactory(
        factory: DaggerViewModelAssistedFactory
    ): ViewModelFactory
}