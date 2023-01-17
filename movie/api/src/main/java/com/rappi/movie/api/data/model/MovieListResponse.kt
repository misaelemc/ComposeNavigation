package com.rappi.movie.api.data.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val results: List<MovieResponseItem>,
    @SerializedName("total_pages") val totalPages: Int
)