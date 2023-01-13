package com.rappi.detail.impl.di

import com.rappi.common.AppScope
import com.rappi.common.FeatureScope
import com.rappi.common.SingleIn
import com.rappi.common.viewModel.ViewModelFactory
import com.rappi.detail.impl.data.datasource.remote.MovieDetailService
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