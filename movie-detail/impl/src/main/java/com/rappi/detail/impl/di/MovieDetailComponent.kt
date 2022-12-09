package com.rappi.detail.impl.di

import com.rappi.common.AppScope
import com.rappi.common.FeatureScope
import com.rappi.common.SingleIn
import com.rappi.detail.impl.data.datasource.remote.MovieDetailService
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUC
import com.rappi.detail.impl.presentation.viewModel.MovieDetailViewModel
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@SingleIn(FeatureScope::class)
@ContributesSubcomponent(
    scope = FeatureScope::class,
    parentScope = AppScope::class
)
interface MovieDetailComponent {

    val movieDetailViewModel: MovieDetailViewModel

    @ContributesTo(AppScope::class)
    interface ParentComponent {
        fun build(): MovieDetailComponent
    }
}

@Module
@ContributesTo(FeatureScope::class)
object MovieDetailModule {

    @Provides
    fun provideMovieDetailService(retrofit: Retrofit): MovieDetailService =
        retrofit.create(MovieDetailService::class.java)

    @Provides
    fun provideMovieDetailViewModel(
        fetchMovieItemUC: FetchMovieItemUC
    ): MovieDetailViewModel = MovieDetailViewModel(fetchMovieItemUC)
}