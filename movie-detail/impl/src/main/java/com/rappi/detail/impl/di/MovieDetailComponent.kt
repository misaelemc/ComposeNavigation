package com.rappi.detail.impl.di

import com.rappi.common.AppScope
import com.rappi.common.FeatureScope
import com.rappi.common.SingleIn
import com.rappi.common.viewModel.ViewModelAssistedFactory
import com.rappi.common.viewModel.ViewModelAssistedFactoryKey
import com.rappi.common.viewModel.ViewModelFactory
import com.rappi.common.viewModel.ViewModelFactoryModule
import com.rappi.detail.impl.data.datasource.remote.MovieDetailService
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUC
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUCImpl
import com.rappi.detail.impl.presentation.viewModel.MovieDetailViewModel
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@SingleIn(FeatureScope::class)
@ContributesSubcomponent(
    scope = FeatureScope::class,
    parentScope = AppScope::class
)
interface MovieDetailComponent {

    fun getFactoryViewModelAssistedFactory(): ViewModelFactory

    @ContributesTo(AppScope::class)
    interface ParentComponent {
        fun create(): MovieDetailComponent
    }
}

@Module
@ContributesTo(FeatureScope::class)
object MovieDetailModule {

    @Provides
    fun provideMovieDetailService(retrofit: Retrofit): MovieDetailService =
        retrofit.create(MovieDetailService::class.java)
}

@Module(includes = [ViewModelFactoryModule::class])
@ContributesTo(FeatureScope::class)
interface MovieDetailBindModule {
    @Binds
    @[IntoMap ViewModelAssistedFactoryKey(MovieDetailViewModel::class)]
    fun bindsOtherViewModelFactory(
        factory: MovieDetailViewModel.Factory
    ): ViewModelAssistedFactory<*>

    @Binds
    fun bindFetchMovieItemUC(impl: FetchMovieItemUCImpl) : FetchMovieItemUC
}
