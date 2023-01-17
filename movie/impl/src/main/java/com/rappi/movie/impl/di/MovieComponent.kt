package com.rappi.movie.impl.di

import com.rappi.common.AppScope
import com.rappi.common.FeatureScope
import com.rappi.common.SingleIn
import com.rappi.common.viewModel.ViewModelAssistedFactory
import com.rappi.common.viewModel.ViewModelAssistedFactoryKey
import com.rappi.common.viewModel.ViewModelFactory
import com.rappi.common.viewModel.ViewModelFactoryModule
import com.rappi.movie.impl.data.datasource.remote.MovieService
import com.rappi.movie.impl.domain.usecase.FetchMoviesUC
import com.rappi.movie.impl.domain.usecase.FetchMoviesUCImpl
import com.rappi.movie.impl.presentation.viewModel.MovieViewModel
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
interface MovieComponent {

    fun getFactoryViewModelAssistedFactory(): ViewModelFactory

    @ContributesTo(AppScope::class)
    interface ParentComponent {
        fun factory(): MovieComponent
    }
}

@Module(includes = [ViewModelFactoryModule::class])
@ContributesTo(FeatureScope::class)
interface MovieBindModule {

    @Binds
    @[IntoMap ViewModelAssistedFactoryKey(MovieViewModel::class)]
    fun bindsMovieViewModel(
        factory: MovieViewModel.Factory
    ): ViewModelAssistedFactory<*>

    @Binds
    fun bindFetchMoviesUC(impl: FetchMoviesUCImpl): FetchMoviesUC
}

@Module
@ContributesTo(FeatureScope::class)
object FeatureModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}