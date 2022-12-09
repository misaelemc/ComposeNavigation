package com.rappi.movie.impl.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rappi.movie.api.domain.model.Movie
import com.rappi.movie.impl.domain.usecase.FetchMoviesUC

class MoviePagingSource constructor(
    private val fetchMoviesUC: FetchMoviesUC
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>) = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val movies = fetchMoviesUC.fetchMovies(nextPage)
            LoadResult.Page(
                data = movies,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}