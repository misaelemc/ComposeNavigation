package com.rappi.movie.impl.domain.repository

import com.rappi.common.paging.Pageable
import com.rappi.movie.api.data.model.MovieResponseItem


interface MovieRepository {

    fun getMoviesPageable(): Pageable<MovieResponseItem>

}