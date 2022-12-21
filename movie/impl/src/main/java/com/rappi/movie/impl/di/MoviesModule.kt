package com.rappi.movie.impl.di

import com.rappi.common.AppScope
import com.rappi.movie.impl.data.datasource.remote.MovieService
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
@ContributesTo(AppScope::class)
object MoviesModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

}