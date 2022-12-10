package com.rappi.detail.impl.di

import com.rappi.common.AppScope
import com.rappi.common.FeatureScope
import com.rappi.common.SingleIn
import com.rappi.detail.impl.data.datasource.remote.MovieDetailService
import com.rappi.detail.impl.domain.usecase.FetchMovieItemUC
import com.rappi.detail.impl.domain.usecase.FetchReviewsByIdUC
import com.rappi.detail.impl.presentation.viewModel.MovieDetailViewModel
import com.rappi.detail.impl.presentation.viewModel.ReviewsViewModel
import com.squareup.anvil.annotations.ContributesSubcomponent
import com.squareup.anvil.annotations.ContributesTo
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Qualifier

@SingleIn(FeatureScope::class)
@ContributesSubcomponent(
    scope = FeatureScope::class,
    parentScope = AppScope::class
)
interface MovieDetailComponent {

    val reviewsViewModel: ReviewsViewModel

    val movieDetailViewModel: MovieDetailViewModel

    @ContributesTo(AppScope::class)
    interface ParentComponent {

        fun createMovieDetailFactory(): Factory
    }

    @ContributesSubcomponent.Factory
    interface Factory {

        fun create(@BindsInstance @MovieId movieId: Int): MovieDetailComponent
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
        @MovieId movieId: Int,
        fetchMovieItemUC: FetchMovieItemUC,
    ): MovieDetailViewModel = MovieDetailViewModel(movieId, fetchMovieItemUC)

    @Provides
    fun provideReviewsViewModel(
        @MovieId movieId: Int,
        fetchReviewsByIdUC: FetchReviewsByIdUC,
    ): ReviewsViewModel = ReviewsViewModel(movieId, fetchReviewsByIdUC)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MovieId