package com.rappi.series.impl.di.data

import com.google.gson.annotations.SerializedName
import com.rappi.series.impl.di.SeriesFeatureScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesService {

    @GET("tv/popular")
    suspend fun fetchSeries(
        @Query("page") page: Int
    ): TvListResponse
}

data class TvListResponse(
    val results: List<SeriesResponseItem>,
    @SerializedName("total_pages") val totalPages: Int
)

data class SeriesResponseItem(
    val id: Int,
    @SerializedName("poster_path") val posterUrl: String?,
    @SerializedName("backdrop_path") val backdropUrl: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("name") val name: String,
    @SerializedName("video") val hasVideo: Boolean,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("imdb_id") val imdbId: String?
)


@Module
@ContributesTo(SeriesFeatureScope::class)
object ServicesModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): SeriesService =
        retrofit.create(SeriesService::class.java)

}
