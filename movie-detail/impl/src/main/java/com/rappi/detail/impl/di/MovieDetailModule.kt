package com.rappi.detail.impl.di

import com.rappi.common.AppScope
import com.rappi.detail.impl.data.datasource.remote.MovieDetailService
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
@ContributesTo(AppScope::class)
object MovieDetailModule {
    @Provides
    fun provideMovieDetailService(retrofit: Retrofit): MovieDetailService =
        retrofit.create(MovieDetailService::class.java)
}
